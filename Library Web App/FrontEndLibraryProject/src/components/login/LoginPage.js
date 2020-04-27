import React from 'react'
import {Link} from "react-router-dom";

export default class LoginPage extends React.Component {
    constructor(props) {
        super(props);
    }

    state = {
        userType : 'Member'
    }

    render() {
        return(
            <div className="container">
                <h1>
                    Login Page
                </h1>

                <select id="userType" className="custom-select" onChange={(event => {
                    const newUserType = event.target.value
                    this.setState({
                        userType : newUserType
                    })
                })}
                value={this.state.userType}>
                    <option value="Member">
                        Member
                    </option>
                    <option value="Librarian">
                        Librarian
                    </option>
                    <option value="Administrator">
                        Administrator
                    </option>
                </select>
                <h3>
                    Username
                </h3>
                <input type="text" className="input-group"/>
                <h3>
                    Password
                </h3>
                <input type="password" className="input-group"/>


                {this.state.userType === 'Member' &&
                 // eslint-disable-next-line react/jsx-no-undef
                    <Link className="btn btn-primary btn-block" to={"/member"} onClick={ () => {
                        console.log('Member')
                    }}>
                        Login Member
                    </Link>
                }
                {this.state.userType === 'Librarian' &&
                 <Link className="btn btn-primary btn-block" to={"/librarian"} onClick={ () => {
                     console.log('Librarian')
                 }}>
                     Login Librarian
                 </Link>
                }
                {this.state.userType === 'Administrator' &&
                 <Link className="btn btn-primary btn-block" to={"/userAdmin"} onClick={ () => {
                     console.log('Administrator')
                 }}>
                     Login Administrator
                 </Link>
                }

            </div>
        )
    }
}
