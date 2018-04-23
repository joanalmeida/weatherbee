import React, { Component } from 'react'
import Paper from 'material-ui/Paper';
import Button from 'material-ui/Button';
import AddIcon from '@material-ui/icons/Add';
import Dialog, {
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
  } from 'material-ui/Dialog';
import { MenuItem } from 'material-ui/Menu';
import Select from 'material-ui/Select';

import axios from 'axios';
import config from '../../config';

import Location from '../Location/Location';

class Board extends Component {
    constructor(props) {
        super(props)
        this.state = {
            openDialog: false,
            location: '',
            availableLocations: [],
            locations: []
        }
        this.closeDialog = this.closeDialog.bind(this)
        this.openDialog = this.openDialog.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.addLocation = this.addLocation.bind(this)
        this.removeLocation = this.removeLocation.bind(this)
    }

    componentDidMount() {
        //Get all locations for adding
        axios.get(config.baseUrl + "api/location/all")
        .then(resp => {
          this.setState(...this.state, {
            availableLocations: resp.data
          })
        })
        .catch(err => {
          console.error("Hubo un error en la conexion");
        });

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

    addLocation() {
        if(this.state.location !== '') {
            axios.post(config.baseUrl + "api/user/" + this.props.userId + "/location", {
                name: this.state.location
            }).then(resp => {
                //Mostrar el snackbar
                console.log("Se agrego " + this.state.location + " correctamente")
            }).catch(err => {
                console.error(err)
            })
        }
        this.closeDialog()
    }

    removeLocation(locId) {
        console.log("Remove loc: " + locId)
    }

    handleChange = event => {
        this.setState({location: event.target.value})
    }

    render() {
        let locationItems = this.state.availableLocations.map(location => {
            return (
                <MenuItem key={location.id} value={location.name}>
                    {location.name}
                </MenuItem>
            )
        })

        let userLocations = this.state.locations.map(loc => {
            return (
                <Paper key={loc.id} style={ {marginTop: "20px"} }>
                    <Location location={loc} onDelete={this.removeLocation}></Location>
                </Paper>
            )
        })

        return (
            <div>
                <div>
                    <Button variant="fab" color="primary" aria-label="add" onClick={this.openDialog}>
                        <AddIcon />
                    </Button>
                    <Dialog
                        open={this.state.openDialog}
                        onClose={this.closeDialog}
                        aria-labelledby="form-dialog-title"
                    >
                        <DialogTitle id="form-dialog-title">Add Location</DialogTitle>
                        <DialogContent>
                            <DialogContentText>
                                Select a City to add to your board
                            </DialogContentText>
                            <Select
                                value={this.state.location}
                                onChange={this.handleChange}
                                inputProps={{
                                    name: 'location',
                                    id: 'location',
                                }}
                                fullWidth
                            >
                            {locationItems}
                            </Select>
                        </DialogContent>
                        <DialogActions>
                            <Button onClick={this.closeDialog} color="primary">
                                Cancel
                            </Button>
                            <Button onClick={this.addLocation} color="primary">
                                Add
                            </Button>
                        </DialogActions>
                    </Dialog>

                    {userLocations}
                </div>
            </div>
        )
    }
}

export default Board