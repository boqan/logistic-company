import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class EmployeeAdd extends Component {
  emptyItem = {
    name: '',
    salary: '',
    officeID: '',
    employeeType: '',
    email: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = { ...this.state.item };
    item[name] = value;
    this.setState({ item });
  }

  async handleSubmit(event) {
    event.preventDefault();
    const { item } = this.state;

    const url = '/employee/save';

    await fetch(url, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });

    this.props.history.push('/employees');
  }

  render() {
    const { item } = this.state;
    const title = <h2>Add Employee</h2>;

    return (
      <div>
        <AppNavbar />
        <Container>
          {title}
          <Form onSubmit={this.handleSubmit}>
            <FormGroup>
              <Label for="name">Name</Label>
              <Input type="text" name="name" id="name" value={item.name || ''} onChange={this.handleChange} autoComplete="name" />
            </FormGroup>


            <FormGroup>
              <Label for="salary">Salary</Label>
              <Input type="number" name="salary" id="salary" value={item.salary || ''} onChange={this.handleChange} autoComplete="salary" />
            </FormGroup>

            <FormGroup>
              <Label for="officeID">Office ID</Label>
              <Input type="text" name="officeID" id="officeID" value={item.officeID || ''} onChange={this.handleChange} autoComplete="officeID" />
            </FormGroup>

            <FormGroup>
              <Label for="employeeType">Employee Type</Label>
              <Input type="text" name="employeeType" id="employeeType" value={item.employeeType || ''} onChange={this.handleChange} autoComplete="employeeType" />
            </FormGroup>

            <FormGroup>
              <Label for="email">Email</Label>
              <Input type="text" name="email" id="email" value={item.email || ''} onChange={this.handleChange} autoComplete="email" />
            </FormGroup>

            <FormGroup>
              <Button color="primary" type="submit">Save</Button>{' '}
              <Button color="secondary" tag={Link} to="/employees">Cancel</Button>
            </FormGroup>
          </Form>
        </Container>
      </div>
    );
  }
}

export default withRouter(EmployeeAdd);
