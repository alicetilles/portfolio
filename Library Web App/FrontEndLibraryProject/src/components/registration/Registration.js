import React from 'react'
import {Link} from "react-router-dom";

export default class Registration extends React.Component {
    constructor(props) {
        super(props);
    }

    state = {
        userType : '',
        firstName: '',
        lastName: '',
        username: '',
        password: '',
        email: '',
        dtype: 'Library Member',
        dateOfBirth: '',
        sponsoredBy: '',
        dateHired: '',
        hasW2OnFile: '',
        returnedUser: {}

    };

    register = (user) => {
        {this.state.dtype === 'Library Member' &&
         fetch(`http://localhost:8080/api/members`, {
             method: 'POST',
             body: JSON.stringify(user),
             headers: {
                 'content-type': 'application/json'
             },
         })
             .then(response => response.json())
             .then(result => this.setState({
                                                 returnedUser: result
                                             }))
        }
    }


    render() {
        return(
            <div className="container">
                <h4>
                    Member Registration
                </h4>
                <h4 >
                    First Name
                </h4>

                <input placeholder="First Name" type="text" className="input-group"
                       onChange={ (e) =>
                           this.setState({
                                             firstName: e.target.value
                                         })
                       }/>
                <br/>
                <h4 >
                    Last Name
                </h4>
                <input placeholder="Last Name" type="text" className="input-group"
                       onChange={ (e) =>
                           this.setState({
                                             lastName: e.target.value
                                         })
                       }/>
                <br/>
                <h4 >
                    Username
                </h4>
                <input placeholder="Username" type="text" className="input-group"
                       onChange={ (e) =>
                           this.setState({
                                             username: e.target.value
                                         })
                       }/>
                <br/>
                <h4 >
                    Password
                </h4>
                <input placeholder="Password" type="password" className="input-group"
                       onChange={ (e) =>
                           this.setState({
                                             password: e.target.value
                                         })
                       }/>
                <br/>
                <h4 >
                    Email
                </h4>
                <input placeholder="Email" type="text" className="input-group"
                       onChange={ (e) =>
                           this.setState({
                                             email: e.target.value
                                         })
                       }/>
                <br/>
                <h4 >
                    Date of Birth
                </h4>
                <input type="date" className="input-group"
                       onChange={ (e) =>
                           this.setState({
                                             dateOfBirth: e.target.value
                                         })
                       }/>
                <br/>
                <h4 >
                    Sponsor Id
                </h4>
                <input placeholder="13 and under members must have a sponsor to register." type="number" className="input-group"
                       onChange={ (e) =>
                           this.setState({
                                             sponsoredBy: e.target.value
                                         })
                       }/>
                <br/>


                <Link className="btn btn-primary btn-block"

                        onClick={ () => {
                            let user = {
                                firstName: this.state.firstName,
                                lastName: this.state.lastName,
                                username: this.state.username,
                                password: this.state.password,
                                email: this.state.email,
                                dtype: this.state.dtype,
                                dateOfBirth: this.state.dateOfBirth,
                                sponsoredBy: this.state.sponsoredBy,
                                // dateHired: this.state.dateHired,
                                // hasW2OnFile: this.state.hasW2OnFile
                            };
                            this.register(user)
                        }}
                      to={"/"}
                >
                    Registration
                </Link>
            </div>
        )
    }
}
