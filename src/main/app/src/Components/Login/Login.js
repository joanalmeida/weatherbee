import React, { Component } from 'react'
import Typography from 'material-ui/Typography';
import Paper from 'material-ui/Paper';
import TextField from 'material-ui/TextField';
import Grid from 'material-ui/Grid';
import Button from 'material-ui/Button';

class Login extends Component {
    constructor(props) {
        super(props)
        this.state = {
            email: '',
            password: ''
        }
    }

    handleChange = prop => event => {
        this.setState({ [prop]: event.target.value })
    }

    logIn() {
        this.props.onLogin({
            "email": this.state.email,
            "password": this.state.password
        });
    }

    render() {
        return (
            <Paper>
                <Typography variant="title" style={ {textAlign: "center"} }>Login</Typography>
                <form>
                    <Grid container spacing={16} justify="center">
                        <Grid item xs={8}>
                            <TextField fullWidth
                                id="email"
                                label="Email"
                                onChange={this.handleChange('email')}
                            />
                            <TextField fullWidth
                                id="password"
                                label="Password"
                                type="password"
                                onChange={this.handleChange('password')}
                            />
                            <Button variant="raised" color="primary" onClick={this.logIn.bind(this)}>
                                Login
                            </Button>
                        </Grid>
                    </Grid>
                </form>
            </Paper>
        )
    }
}

export default Login;