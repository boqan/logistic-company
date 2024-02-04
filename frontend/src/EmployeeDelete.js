import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';

class EmployeeDelete extends Component {

    constructor(props) {
        super(props);
        this.state = { employees: [] };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/employee/getAll')
            .then(response => response.json())
            .then(data => this.setState({ employees: data }));
    }

    async remove(email) {
    const url = `/employee/deleteByEmail/${email}`;
        await fetch(url, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedEmployees = [...this.state.employees].filter(employee => employee.email !== email);
            this.setState({ employees: updatedEmployees });
        });
    }

    render() {
        const { employees, isLoading } = this.state;

        if(isLoading) {
            return <p>Loading...</p>;
        }

        const employeeList = employees.map(employee => {
           return <tr key={employee.email}>
               {/* This is for the Employee page - displays the employees and the buttons */}
                <td style={{ whiteSpace: 'nowrap' }}>{employee.name}</td>
                <td>{employee.salary}</td>
                <td>{employee.officeID}</td>
                <td>{employee.employeeType}</td>
                <td>{employee.email}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/employee/updateByEmail/" + employee.email}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(employee.email)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
          <div>
            <AppNavbar />
            <Container fluid>
              <div className="d-flex justify-content-between align-items-center mb-3">
                <h3>Employees</h3>
                {/* This is for the Add Employee logic */}
                <Button color="success" tag={Link} to="/employee/new">Add Employee</Button>
              </div>

              <Table className="mt-4">
                <thead>
                  <tr>
                    <th width="30%">Name</th>
                    <th width="30%">Salary</th>
                    <th width="20%">Office ID</th>
                    <th width="10%">Employee Type</th>
                    <th width="10%">Email</th>
                  </tr>
                </thead>
                <tbody>{employeeList}</tbody>
              </Table>
            </Container>
          </div>
        );

    }
}

export default EmployeeDelete;

