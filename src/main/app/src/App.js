import React, { Component, Fragment } from 'react';
import axios from 'axios';
import config from './config';
import './App.css';
import Header from './Components/Header/Header';
import Login from './Components/Login/Login';

import Grid from 'material-ui/Grid';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      locations: [],
      fetchingLocations: true,
      loggedUser: false,
      user: {}
    }
  }

  componentDidMount() {
    axios.get(config.baseUrl + "locations")
    .then(data => {
      console.log(data.locations);
      this.setState(...this.state, {
        fetchingLocations: false,
        locations: this.state.locations.concat(data.locations)
      })
    })
    .catch(err => {
      console.error("Hubo un error en la conexion");
    });
  }

  doSomethingAwesome() {
    console.log("Doing awesome stuff");
    axios.post(config.baseUrl + "locations", {
      "name": "Vancouver"
    }).then(res => {
      console.log(res);
    })
  }

  login(userData) {
    axios.post(config.baseUrl + "login", userData)
    .then(res => {
      this.setState(...this.state, {
        loggedUser: true,
        user: res.data
      })
    })
    .catch(err => {
      console.error(err)
    })
  }

  render() {
    let appName = this.state.loggedUser ? this.state.user.name : "weatherbee";

    return (
      <Fragment>
        <Header appName={appName}/>
        <Grid container spacing={24} justify="center">
          <Grid item xs={12} sm={6}>
            <Login onLogin={(user) => this.login(user)}/>
          </Grid>
        </Grid>
        {
          /*
          this.state.fetchingLocations ?
            <div>Loading...</div> :
            <div>
              Tengo data para usar
              <button onClick={() => this.login()}>Login</button>
            </div>
            */
          this.state.loggedUser ?
            <div>Estas logueado</div>
            :
            <div>Login plz</div>
        }
      </Fragment>
    )
  }
}

export default App;
