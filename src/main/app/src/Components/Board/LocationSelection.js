import React, { Component } from 'react'
import config from '../../config';
import axios from 'axios';

import Button from 'material-ui/Button';
import Dialog, {
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
  } from 'material-ui/Dialog';
import { MenuItem } from 'material-ui/Menu';
import Select from 'material-ui/Select';

class LocationSelection extends Component {
    constructor(props) {
        super(props)
        this.state = {
            availableLocations: [],
            location: ''
        }

        this.handleChange = this.handleChange.bind(this)
        this.addLocation = this.addLocation.bind(this)
    }

    componentDidMount() {
        //Get all locations for dialog selection
        axios.get(config.baseUrl + "api/location/all")
        .then(resp => {
          this.setState(...this.state, {
            availableLocations: resp.data
          })
        })
        .catch(err => {
          console.error("Hubo un error en la conexion");
        });
    }

    handleChange = event => {
        this.setState({location: event.target.value})
    }

    addLocation() {
        if(this.state.location !== '') {
            this.props.addLocation(this.state.location)
        }
        this.props.closeDialog()
    }



    //Recibo openDialog prop
    //Recibo closeDialog func
    //Recibo locationItems obj
    //Recibo addLocation func
    render() {
        let locationItems = this.state.availableLocations.map(location => {
            return (
                <MenuItem key={location.id} value={location.name}>
                    {location.name}
                </MenuItem>
            )
        })

        return (
            <Dialog
                open={this.props.openDialog}
                onClose={this.props.closeDialog}
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
                    <Button onClick={this.props.closeDialog} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={this.addLocation} color="primary">
                        Add
                    </Button>
                </DialogActions>
            </Dialog>
        )
    }
}

export default LocationSelection
