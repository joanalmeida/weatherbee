import React, { Component } from 'react'
import Typography from 'material-ui/Typography';
import Paper from 'material-ui/Paper';
import TextField from 'material-ui/TextField';
import Grid from 'material-ui/Grid';
import Button from 'material-ui/Button';

class Register extends Component {
    constructor(props) {
        super(props)
        this.state = {
            email: '',
            password: '',
            name: ''
        }
    }

    handleChange = field => event => {
        this.setState({ [field]: event.target.value })
    }

    register() {
        this.props.onRegistry({
            email: this.state.email,
            password: this.state.password,
            name: this.state.name
        })
    }

    handleSubmit(e) {
        //Default del submit es cargar otra pagina
        e.preventDefault()
        if(this.props.email && this.props.password) {
            this.register()
        } else {
            //Present some error
            console.error("Email o password vacios")
        }
    }

    render() {
        return (
            <Paper style={ {textAlign: "center", marginTop: "20px"} }>
                <Typography variant="title">Register</Typography>
                <form onSubmit={this.handleSubmit.bind(this)}>
                    <Grid container spacing={16} justify="center">
                        <Grid item xs={8}>
                            <TextField fullWidth
                                id="name"
                                label="Name"
                                onChange={this.handleChange('name')}
                            />
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
                            <Button type="submit" variant="raised" color="primary" onClick={this.register.bind(this)}>
                                Register
                            </Button>
                        </Grid>
                    </Grid>
                </form>
            </Paper>
        )
    }
}

export default Register