import React from 'react'
import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import Typography from 'material-ui/Typography';

let styles = {
    title: {
        textTransform: "capitalize",
        width: "100%",
        textAlign: "center"
    }
}

export default props => 
    <AppBar position="static">
        <Toolbar>
            <Typography variant="title" color="inherit" style={ styles.title }>
                {props.title}
            </Typography>
        </Toolbar>
    </AppBar>