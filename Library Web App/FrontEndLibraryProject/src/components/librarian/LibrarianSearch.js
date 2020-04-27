import React from "react";
import {Link} from "react-router-dom";

export default class LibrarianSearch extends React.Component {
    state = {
        librarians: {},
        librarianUsernameSearched: ''
    }

    componentDidMount() {
        let searchLibrarian = this.props.match.params.librarianSearchedFor
        console.log("LIBRARIAN SEARCHED FOR", searchLibrarian)
        console.log(this.props.match.params)
        if(searchLibrarian === undefined) {
            searchLibrarian = ' '
        }
        fetch(`http://localhost:8080/api/librarians/username/${searchLibrarian}`)
            .then(response => response.json())
            .then(results => this.setState({
                                               librarians: results,
                                               librarianUsernameSearched: searchLibrarian
                                           }))
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(prevProps.match.params.librarianSearchedFor !== this.props.match.params.librarianSearchedFor) {
            this.findUserByUsername(this.props.match.params.librarianSearchedFor)
        }
    }

    findUserByUsername = (username) =>
        fetch(`http://localhost:8080/api/librarians/username/${username}`)
            .then(response => response.json())
            .then(results => this.setState({
                                               librarians: results
                                           }))

    render() {
        return(
            <div>
                <h1>
                    Librarian Username: ({this.props.match.params.librarianSearchedFor})
                </h1>

                <ul className="list-group">
                    <span className="list-group-item">
                        <input
                            value={this.state.librarianUsernameSearched || ''}
                            onChange={(e) => this.setState({
                                                               librarianUsernameSearched: e.target.value
                                                           })}
                            className={`form-control`}/>

                        {/*<button*/}
                        {/*    onClick={() => this.props.history.push(`/librarian-search/${this.state.librarianUsernameSearched}`)}*/}
                        {/*    className={`btn btn-primary btn-block`}>*/}
                        {/*    Search Librarian*/}
                        {/*</button>*/}
                        {this.props.match.path.toString().includes("user-management") &&
                         <button
                             onClick={() => this.props.history.push(`/user-management/admin/librarian-search/${this.state.librarianUsernameSearched}`)}
                             className={`btn btn-primary btn-block`}>
                             Search Librarian
                         </button>
                        }

                        {!this.props.match.path.toString().includes("user-management") &&
                         <button
                             onClick={() => this.props.history.push(`/librarian-search/${this.state.librarianUsernameSearched}`)}
                             className={`btn btn-primary btn-block`}>
                             Search Librarian
                         </button>
                        }
                    </span>

                    {/*{this.state.librarians &&*/}
                    {/* <Link to={`/librarian-profile/${this.state.librarians.id}`}>*/}
                    {/*     {this.state.librarians.id}*/}
                    {/*     <br/>*/}
                    {/*     {this.state.librarians.username}*/}
                    {/* </Link>*/}
                    {/*}*/}
                    {this.state.librarians && !this.props.match.path.toString().includes("user-management") &&
                     <Link to={`/librarian-profile/${this.state.librarians.id}`}>
                         {this.state.librarians.id}
                         <br/>
                         {this.state.librarians.username}
                     </Link>
                    }

                    {this.state.librarians && this.props.match.path.toString().includes("user-management") &&
                     <Link to={`/user-management/admin/librarian-profile/${this.state.librarians.id}`}>
                         {this.state.librarians.id}
                         <br/>
                         {this.state.librarians.username}
                     </Link>
                    }
                </ul>
            </div>
        )
    }
}
