import React, { Component, Fragment } from 'react'
import Paper from 'material-ui/Paper';
import Button from 'material-ui/Button';
import Switch from 'material-ui/Switch';
import { FormGroup, FormControlLabel } from 'material-ui/Form';
import Snackbar from 'material-ui/Snackbar';
import IconButton from 'material-ui/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import AddIcon from '@material-ui/icons/Add';
import axios from 'axios';
import config from '../../config';

import Location from '../Location/Location';
import LocationSelect from './LocationSelection';
import Forecast from './Forecast';

import SockJs from 'sockjs-client';
//import Stomp from 'stompjs';
import Stomp from '@stomp/stompjs';

const forecastSort = (a, b) => {
    if(new Date(a.date) > new Date(b.date)) {
        return 1;
    }
    if(new Date(a.date) < new Date(b.date)) {
        return -1;
    }
    return 0;
}

const styles = {
    buttonRow: {
        display: 'flex',
        justifyContent: 'flex-end',
        marginTop: '20px'
    }
}

class Board extends Component {
    constructor(props) {
        super(props)
        this.state = {
            openDialog: false,
            openModal: false,
            location: '',
            locations: [],
            showForecast: false,
            currentForecast: [],
            openSnackbar: false,
            snackbarMsg: ''
        }
        this.closeDialog = this.closeDialog.bind(this)
        this.openDialog = this.openDialog.bind(this)
        this.addLocation = this.addLocation.bind(this)
        this.removeLocation = this.removeLocation.bind(this)
        this.getForecast = this.getForecast.bind(this)
        this.forecastBack = this.forecastBack.bind(this)
        this.openModal = this.openModal.bind(this)
        this.closeModal = this.closeModal.bind(this)
        this.startLiveFeed = this.startLiveFeed.bind(this)
        this.processLiveFeed = this.processLiveFeed.bind(this)
        this.handleLifeFeed = this.handleLifeFeed.bind(this)
        this.closeSnackbar = this.closeSnackbar.bind(this)

        this.socket;
    }

    componentDidMount() {
        axios.get(config.baseUrl + "api/user/" + this.props.userId + "/location")
        .then(resp => {
            this.setState({locations: resp.data})
        })
        .catch(err => {
            console.error("No se pudieron obtener las locations del user")
        })
    }

    openDialog() {
        this.setState({openDialog: true});
    }

    closeDialog() {
        this.setState({openDialog: false, location: ''});
    }

    addLocation(locName) {
        axios.post(config.baseUrl + "api/user/" + this.props.userId + "/location", {
            name: locName
        }).then(resp => {
            this.setState({
                locations: resp.data,
                snackbarMsg: "Se agrego " + locName + " a tus ciudades",
                openSnackbar: true
            })
        }).catch(err => {
            console.error(err)
        })
    }

    removeLocation(locId) {
        axios.delete(config.baseUrl + "api/user/" + this.props.userId + "/location/" + locId)
        .then(resp => {
            this.setState({locations: resp.data})
        }).catch(err => {
            console.error(err)
        })
    }

    getForecast(locId) {
        axios.get(config.baseUrl + "api/location/" + locId + "/forecast")
        .then(resp => {
            resp.data.sort(forecastSort);
            this.setState({currentForecast: resp.data, showForecast: true})
        }).catch(err => {
            console.error(err)
        })
    }

    forecastBack() {
        this.setState({showForecast: false})
    }

    openModal = () => {
        this.setState({openModal: true})
    }

    closeModal = () => {
        this.setState({openModal: false})
    }

    handleLifeFeed = () => {
        if(this.state.livefeed) {
            this.socket.close()
            //Reinicio el socket
            this.socket = null;
        } else {
            this.startLiveFeed()
        }
    }

    startLiveFeed = () => {
        console.log("Starting live feed...")
        if(!this.socket) {
            this.socket = SockJs('livefeed')
        }
        let stompClient = Stomp.over(this.socket)
        stompClient.debug = () => {} //Disable horrible debug msgs

        stompClient.connect({}, (frame) => {
            stompClient.subscribe(config.baseUrl + 'topic/updateLocation', this.processLiveFeed);
        }, (err) => {
            this.setState({
                livefeed: false,
                snackbarMsg: "Se perdio la conexion en vivo",
                openSnackbar: true
            })
        });
        
        this.setState({livefeed: true})
    }

    processLiveFeed(feed) {
        let updatedLocations = []
        JSON.parse(feed.body).forEach(loc => {
            let existentLoc = this.state.locations.find((currentLoc) => {
                return loc.id === currentLoc.id;
            })
            if(existentLoc){
                updatedLocations.push(loc)
            }
        })

        this.setState({
            locations: updatedLocations,
            snackbarMsg: "Se actualizaron todas las ciudades",
            openSnackbar: true
        })
    }

    closeSnackbar() {
        this.setState({openSnackbar: false})
    }

    render() {
        let userLocations = this.state.locations.map(loc => {
            return (
                <Paper key={loc.id} style={ {marginTop: "20px"} }>
                    <Location 
                        location={loc}
                        onDelete={this.removeLocation}
                        onForecast={this.getForecast}
                    ></Location>
                </Paper>
            )
        })

        const mainContent = this.state.showForecast ? (
            <Fragment>
                <Forecast forecast={this.state.currentForecast} onBack={this.forecastBack}/>
            </Fragment>
        ) : (
            <Fragment>
                <div style={ styles.buttonRow }>
                    <FormGroup>
                        <FormControlLabel
                            control={
                                <Switch
                                    checked={this.state.livefeed}
                                    onChange={this.handleLifeFeed}
                                    value="livefeed"
                                />
                            }
                            label="Live feed"
                        />
                    </FormGroup>
                    <Button variant="fab" color="primary" aria-label="add" onClick={this.openDialog}>
                        <AddIcon />
                    </Button>
                    <Snackbar
                        anchorOrigin={{
                            vertical: 'bottom',
                            horizontal: 'left',
                        }}
                        open={this.state.openSnackbar}
                        autoHideDuration={6000}
                        onClose={this.closeSnackbar}
                        message={this.state.snackbarMsg}
                        action={
                            <IconButton
                                key="close"
                                aria-label="Close"
                                color="inherit"
                                onClick={this.closeSnackbar}
                                >
                                <CloseIcon />
                            </IconButton>
                        }
                    />
                </div>
                <LocationSelect
                    openDialog={this.state.openDialog}
                    closeDialog={this.closeDialog}
                    addLocation={this.addLocation}
                >
                </LocationSelect>
                <div style={ {marginBottom: '30px'} }>
                    {userLocations}
                </div>
            </Fragment>
        );

        return (
            <div>
                {mainContent}
            </div>
        )
    }
}

export default Board