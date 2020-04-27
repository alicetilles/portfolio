import React from 'react';
import {Link} from "react-router-dom";

export default class AllUsersView extends React.Component {

    state = {
        libraryMembers: [],
        librarians: [],
        allUsers:[]
    }

    componentDidMount() {

        fetch(`http://localhost:8080/api/members`)
            .then(response => response.json())
            .then(results => this.setState({
                                               libraryMembers: results,
                                           }))
        fetch(`http://localhost:8080/api/librarians`)
            .then(response => response.json())
            .then(results => this.setState({
                                               librarians: results,
                                           }))

    }

    render() {
        return(
            <div>
                <h1>
                    All Users
                </h1>
                <ul className="list-group">

                    <h1>All Library Members</h1>
                    {this.state.libraryMembers
                     && this.state.libraryMembers.map((users, index) =>
                                                 <li key={index} className="list-group-item">
                                                     <Link to={`/user-management/admin/member-profile/${users.id}`}>
                                                         {users.username}
                                                     </Link>
                                                     {console.log("Librarian Member", index)}
                                                 </li>
                    )
                    }
                    <br/>
                    <h1>All Librarians</h1>

                    {this.state.librarians
                     && this.state.librarians.map((librarian, index) =>
                                                    <li key={index} className="list-group-item">
                                                        <Link to={`/user-management/admin/librarian-profile/${librarian.id}`}>
                                                            {librarian.username}
                                                        </Link>
                                                        {console.log("Librarian", index)}
                                                    </li>
                    )
                    }
                    <br/>

                </ul>
            </div>
        )
    }
}
