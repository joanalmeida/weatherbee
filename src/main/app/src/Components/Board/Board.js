import React, { Component, Fragment } from 'react'
import Paper from 'material-ui/Paper';
import Button from 'material-ui/Button';
import AddIcon from '@material-ui/icons/Add';
import axios from 'axios';
import config from '../../config';

import Location from '../Location/Location';
import LocationSelect from './LocationSelection';
import Forecast from './Forecast';

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
        flexDirection: 'column',
        alignItems: 'flex-end',
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
            currentForecast: []
        }
        this.closeDialog = this.closeDialog.bind(this)
        this.openDialog = this.openDialog.bind(this)
        this.addLocation = this.addLocation.bind(this)
        this.removeLocation = this.removeLocation.bind(this)
        this.getForecast = this.getForecast.bind(this)
        this.forecastBack = this.forecastBack.bind(this)
        this.openModal = this.openModal.bind(this)
        this.closeModal = this.closeModal.bind(this)
    }

    componentDidMount() {
        axios.get(config.baseUrl + "api/user/" + this.props.userId + "/location")
        .then(resp => {
            console.log("Las loc del user: ")
            console.log(resp.data)
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
            //Mostrar el snackbar
            console.log("Se agrego " + locName + " correctamente")
            this.setState({locations: resp.data})
        }).catch(err => {
            console.error(err)
        })
    }

    removeLocation(locId) {
        console.log("Remove loc: " + locId)
        axios.delete(config.baseUrl + "api/user/" + this.props.userId + "/location/" + locId)
        .then(resp => {
            //Mostrar el snackbar
            console.log("Se elimino la loc con id " + locId + " correctamente")
            this.setState({locations: resp.data})
        }).catch(err => {
            console.error(err)
        })
    }

    getForecast(locId) {
        console.log("Conseguir el forecast de: " + locId)
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
                    <Button variant="fab" color="primary" aria-label="add" onClick={this.openDialog}>
                        <AddIcon />
                    </Button>
                </div>
                <LocationSelect
                    openDialog={this.state.openDialog}
                    closeDialog={this.closeDialog}
                    addLocation={this.addLocation}
                >
                </LocationSelect>
                <div>{userLocations}</div>
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