import React, { Component, Fragment } from 'react';
import axios from 'axios';
import config from './config';
import './App.css';
import { MuiThemeProvider, createMuiTheme } from 'material-ui/styles';
import blue from 'material-ui/colors/blue';
import teal from 'material-ui/colors/teal';
import Header from './Components/Header';
import Login from './Components/Login';
import Register from './Components/Register';
import Board from './Components/Board';

import Grid from 'material-ui/Grid';

const theme = createMuiTheme({
  palette: {
    primary: teal,
    secondary: blue
  }
});

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      locations: [],
      fetchingLocations: true,
      loggedUser: false,
      register: false,
      user: {}
    }
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

  register(userData) {
    axios.post(config.baseUrl + "register", userData)
    .then(res => {
      //TODO: Show a nice registration ok msg
      console.log("Registration ok")
      this.setState(...this.state, {
        register: false
      })
    })
    .catch(err => {
      console.error(err)
    })
  }

  showRegister() {
    this.setState(...this.state, {
      register: true
    })
  }

  hideRegister() {
    this.setState(...this.state, {
      register: false
    })
  }

  render() {
    let title = this.state.loggedUser ? this.state.user.name + "'s Board" : "weatherBee";

    return (
      <MuiThemeProvider theme={theme}>
        <Fragment>
          <Header title={title}/>
          <Grid container justify="center">
            <Grid item xs={12} sm={8} style={ {padding: "0px 20px"} }>
              {
                this.state.register ?
                  <Register
                    onRegistry={(userData) => this.register(userData)}
                    onBack={() => this.hideRegister()}
                   /> :
                  this.state.loggedUser ?
                    <Board userId={this.state.user.id}/> :
                    <Login 
                      onLogin={(user) => this.login(user)}
                      onRegistry={this.showRegister.bind(this)}
                    />
              }
            </Grid>
          </Grid>
        </Fragment>
      </MuiThemeProvider>
    )
  }
}

export default App;
