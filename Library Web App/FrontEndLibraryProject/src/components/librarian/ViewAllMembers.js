import React from 'react';
import {Link} from "react-router-dom";

export default class ViewAllMembers extends React.Component {

    state = {
        libraryMembers: [],

    }

    componentDidMount() {

        fetch(`http://localhost:8080/api/members`)
            .then(response => response.json())
            .then(results => this.setState({
                                               libraryMembers: results,
                                           }))
    }

    render() {
        return(
            <div>
                <h1>
                    All Library Members
                </h1>
                <ul className="list-group">
                    {this.props.match.path.toString().includes("/librarian/") &&
                        this.state.libraryMembers
                              && this.state.libraryMembers.map((users, index) =>
                                                                   <li key={index} className="list-group-item">
                                                                       <Link to={`/user-management/librarian/member-profile/${users.id}`}>
                                                                           {users.username}
                                                                       </Link>
                                                                       {console.log("Librarian Member", index)}
                                                                   </li>
                                )

                    }
                    {this.props.match.path.toString().includes("/member/") &&
                     this.state.libraryMembers
                     && this.state.libraryMembers.map((users, index) =>
                                                          <li key={index} className="list-group-item">
                                                              <Link to={`/member-profile/${users.id}`}>
                                                                  {users.username}
                                                              </Link>
                                                          </li>
                    )

                    }





                    <br/>

                </ul>
            </div>
        )
    }
}
