import React, { Component, Fragment } from 'react'
import Table, { TableBody, TableCell, TableHead, TableRow } from 'material-ui/Table';
import Paper from 'material-ui/Paper';
import Button from 'material-ui/Button';

//TODO: Ordenar el forecast en algun lugar
//Max -> High
//Min -> Low
//Description -> Text
class Forecast extends Component {
    render() {
        const rows = this.props.forecast.map((forecast) => {
            return (
                <TableRow key={forecast.id}>
                    <TableCell>{forecast.date}</TableCell>
                    <TableCell>{forecast.day}</TableCell>
                    <TableCell>{forecast.high}</TableCell>
                    <TableCell>{forecast.low}</TableCell>
                    <TableCell>{forecast.text}</TableCell>
                </TableRow>
            )
        })

        return (
            <Fragment>
                <Button onClick={ this.props.onBack }>Back</Button>
                <Paper style={ {minWidth: '630px', marginTop: '50px', marginBottom: '25px'} }>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>Date</TableCell>
                                <TableCell>Day</TableCell>
                                <TableCell>Max</TableCell>
                                <TableCell>Min</TableCell>
                                <TableCell>Description</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {rows}
                        </TableBody>
                    </Table>
                </Paper>
            </Fragment>
        )
    }
}

export default Forecast