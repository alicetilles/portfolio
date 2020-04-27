import React from "react";
import {Link} from "react-router-dom";

export default class MemberSearch extends React.Component {
    state = {
        members: {},
        memberUsernameSearched: ''
    }

    componentDidMount() {
        let searchMember = this.props.match.params.memberSearchedFor
        console.log("MEMBER SEARCHED FOR", searchMember)
        console.log(this.props.match.params)
        if(searchMember === undefined) {
            searchMember = ' '
        }
        fetch(`http://localhost:8080/api/members/username/${searchMember}`)
            .then(response => response.json())
            .then(results => this.setState({
                                            members: results,
                                            memberUsernameSearched: searchMember
                                           }))
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(prevProps.match.params.memberSearchedFor !== this.props.match.params.memberSearchedFor) {
            this.findUserByUsername(this.props.match.params.memberSearchedFor)
        }
    }

    findUserByUsername = (username) =>
        fetch(`http://localhost:8080/api/members/username/${username}`)
            .then(response => response.json())
            .then(results => this.setState({
                                               members: results
                                           }))

    render() {
        return(
            <div>
                <h1>
                    Member Username: ({this.props.match.params.memberSearchedFor})
                </h1>

                <ul className="list-group">
                    <span className="list-group-item">
                        <input
                            value={this.state.memberUsernameSearched || ''}
                            onChange={(e) => this.setState({
                                                               memberUsernameSearched: e.target.value
                                                           })}
                            className={`form-control`}/>



                        {this.props.match.path.toString().includes("admin") &&
                         <button
                             onClick={() => this.props.history.push(`/user-management/admin/member-search/${this.state.memberUsernameSearched}`)}
                             className={`btn btn-primary btn-block`}>
                             Search User
                         </button>
                        }
                        {this.props.match.path.toString().includes("librarian") &&
                         <button
                             onClick={() => this.props.history.push(`/user-management/librarian/member-search/${this.state.memberUsernameSearched}`)}
                             className={`btn btn-primary btn-block`}>
                             Search User
                         </button>
                        }

                        {!this.props.match.path.toString().includes("user-management") &&
                         <button
                             onClick={() => this.props.history.push(`/member-search/${this.state.memberUsernameSearched}`)}
                             className={`btn btn-primary btn-block`}>
                             Search User
                         </button>
                        }



                        {/*<button*/}
                        {/*    onClick={() => this.props.history.push(`/member-search/${this.state.memberUsernameSearched}`)}*/}
                        {/*    className={`btn btn-primary btn-block`}>*/}
                        {/*    Search User*/}
                        {/*</button>*/}

                    </span>

                    {this.state.members && !this.props.match.path.toString().includes("user-management") &&
                            <Link to={`/member-profile/${this.state.members.id}`}>
                                {this.state.members.id}
                                <br/>
                                {this.state.members.username}
                            </Link>
                    }

                    {this.state.members && this.props.match.path.toString().includes("admin") &&
                     <Link to={`/user-management/admin/member-profile/${this.state.members.id}`}>
                         {this.state.members.id}
                         <br/>
                         {this.state.members.username}
                     </Link>
                    }
                    {this.state.members && this.props.match.path.toString().includes("librarian") &&
                     <Link to={`/user-management/librarian/member-profile/${this.state.members.id}`}>
                         {this.state.members.id}
                         <br/>
                         {this.state.members.username}
                     </Link>
                    }
                </ul>
            </div>
        )
    }
}
