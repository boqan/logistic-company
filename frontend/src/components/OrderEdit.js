import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class OrderEdit extends Component {

    emptyItem = {
      receiverAddress: '',
      senderId: 0, 
      weight: 0.0, 
      officeId: 0, 
      deliveryType: '', 
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem

        };
        this.handleChange = this.handleChange.bind(this);
        this.handleChangeNumber = this.handleChangeNumber.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (isNaN(this.props.match.params.id)) {
            const client = await (await fetch(`/order/${this.props.match.params.id}`)).json();
            this.setState({item: client});
        }
    }
    handleChangeNumber(event) {
      const { name, value } = event.target;
      // Parse the value as a double or use 0 if parsing fails
      const doubleValue = parseFloat(value) || 0.0;
      
      this.setState({
        item: {
          ...this.state.item,
          [name]: doubleValue,
        },
      });
    }
    

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }
    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
    
        await fetch('/order' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/orders');
    }


    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Order' : 'Add Order'}</h2>;
    
        return (
            <div>
              <AppNavbar />
              <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                  <FormGroup>
                    <Label for="receiverAddress">Receiver Address</Label>
                    <Input
                      type="text"
                      name="receiverAddress"
                      id="receiverAddress"
                      value={item.receiverAddress || ''}
                      onChange={this.handleChange}
                      autoComplete="receiverAddress"
                    />
                  </FormGroup>
                  
                  <FormGroup>
                    <Label for="senderId">Sender Id</Label>
                    <Input
                      type="number"
                      name="senderId"
                      id="senderId"
                      value={item.senderId || ''}
                      onChange={this.handleChangeNumber}
                      autoComplete="senderId"
                    />
                  </FormGroup>
                  <FormGroup>
                    <Label for="weight">Weight</Label>
                    <Input
                      type="number"
                      name="weight"
                      id="weight"
                      value={item.weight || ''}
                      onChange={this.handleChangeNumber}
                      autoComplete="weight"
                    />
                  </FormGroup>
                  <FormGroup>
                    <Label for="officeId">Office Id</Label>
                    <Input
                      type="number"
                      name="officeId"
                      id="officeId"
                      value={item.officeId || ''}
                      onChange={this.handleChangeNumber}
                      autoComplete="officeId"
                    />
                  </FormGroup>
                  <Label for="deliveryType">Delivery Type</Label>
                    <Input
                        type="select"
                        name="deliveryType"
                        id="deliveryType"
                        value={item.deliveryType || ''}
                        onChange={this.handleChange}
                        autoComplete="deliveryType"
                    >
                        <option value="TO_HOME_ADDRESS">To home address</option>
                        <option value="TO_OFFICE">To office</option>
                    </Input>
                  <FormGroup>
                    <Button color="primary" type="submit" tag={Link} to="/orders">
                      Save
                    </Button>{' '}
                    <Button color="secondary" tag={Link} to="/orders">
                      Cancel
                    </Button>
                  </FormGroup>
                </Form>
              </Container>
            </div>
        );
                
    }
}
export default withRouter(OrderEdit);