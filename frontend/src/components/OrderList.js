import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';



class OrdersList extends Component {

    constructor(props) {
        super(props);
        this.state = {orders: []};
        this.remove = this.remove.bind(this);

    }

    componentDidMount() {
        fetch('/orders')
            .then(response => response.json())
            .then(data => this.setState({orders: data}));
    }
    async remove(id) {
        await fetch(`/order/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedoffices = [...this.state.orders].filter(i => i.id !== id);
            this.setState({orders: updatedoffices});
        });
    }
    
    

    render() {
        const {orders} = this.state;

        const orderList = orders.map(order => {
            return <tr key={order.id}>
                <td style={{whiteSpace: 'nowrap'}}>{order.id}</td>
                <td>{order.receiverAddress}</td>
                <td>${order.price}</td>
                <td>{order.status}</td>

                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary"  tag={Link} to="/order/${order.id}">
                            Edit
                        </Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(order.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/order">
                            Add order
                        </Button>
                    </div>
                    <h3>Orders</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="15%">Id</th>
                            <th width="25%">Receiver Address</th>
                            <th width="15%">Price</th>
                            <th width="20%">Status</th>
                            <th width="25%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {orderList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default OrdersList;