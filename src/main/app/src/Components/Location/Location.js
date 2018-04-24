import React, { Component, Fragment } from 'react'
import Card, { CardActions, CardContent, CardMedia } from 'material-ui/Card';
import Button from 'material-ui/Button';
import Typography from 'material-ui/Typography';

class Location extends Component {
    handleRemove() {
        this.props.onDelete(this.props.location.id)
    }

    render() {
        let { imgUrl, name, condition } = this.props.location
        return (
            <Fragment>
                <Card>
                    <CardMedia
                        style={ {paddingTop: '50%'} }
                        image={imgUrl}
                        title={name}
                    />
                    <CardContent>
                        <Typography gutterBottom variant="headline" component="h2">
                            {name}
                        </Typography>
                        <Typography color="textSecondary">
                            {condition.date}
                        </Typography>
                        <div>
                            Temp: {condition.temp} °C
                        </div>
                        <div>
                            {condition.text}
                        </div>
                    </CardContent>
                    <CardActions>
                        <Button size="small" color="primary">
                            Forecast
                        </Button>
                        <Button size="small" color="primary" onClick={this.handleRemove.bind(this)}>
                            Remove
                        </Button>
                    </CardActions>
                </Card>
            </Fragment>
        )
    }
}

export default Location