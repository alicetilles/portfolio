import React from "react";
import {Link} from "react-router-dom";
import {faTrash} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

export default class MemberProfilePage extends React.Component {
    constructor(props) {
        super(props);
    }
    state = {
        member : {
            username: '',
            password: '',
            email: '',
            dateOfBirth: '',
            firstName: '',
            lastName: '',
            sponsoredBy: ''
        },
        libraryCardExpirationDate: '',
        checkedOutBooks: [],
        sponsorInfo:{},
        editing: false,
        sponsoredList: []

    }

    updateMember = () => {
        fetch(`http://localhost:8080/api/members/${this.props.match.params.memberId}`, {
            method: "PUT",
            body: JSON.stringify(this.state.member),
            headers: {
                'content-type': "application/json"
            }
        })
    }

    changeEditing = (editing) => {
        if (this.state.editing === false) {
            this.setState({
                editing: true
                          })
        } else {
            this.setState({
                editing: false
                          }
            )
        }
    }


    findSponsorInformation = (sponsorId) =>
        fetch(`http://localhost:8080/api/members/id/${sponsorId}`)
            .then(result => result.json())
            .then(response => this.setState({
                sponsorInfo: response
                                            }))

    componentDidMount() {
        const memberId = this.props.match.params.memberId;
        fetch(`http://localhost:8080/api/members/id/${memberId}`)
            .then(response => response.json())
            .then(results => this.setState({
                                               member: {
                                                   username: results.username,
                                                   password: results.password,
                                                   email: results.email,
                                                   dateOfBirth: results.dateOfBirth,
                                                   firstName: results.firstName,
                                                   lastName: results.lastName,
                                                   sponsoredBy: results.sponsoredBy,
                                                   editing: false
                                               }

                                           }))

        fetch(`http://localhost:8080/api/library-cards/member-id/${memberId}`)
            .then(response => response.json())
            .then(results => this.setState({
                                               libraryCardExpirationDate: results.expirationDate
                                               }))

        fetch(`http://localhost:8080/api/members/${memberId}/checked-out-currently`)
            .then(response => response.json())
            // .then(results => console.log(results))
            .then(results => this.setState({
                                               checkedOutBooks: results
                                           }))
        fetch(`http://localhost:8080/api/members/${memberId}/sponsor-recipients`)
            .then(response => response.json())
            .then(results => this.setState({
                                                   sponsoredList: results[0].recipientsOfSponsorship
                                               }))
            // .then(results => console.log(this.state.sponsoredList))

    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevState.member !== this.state.member) {
            this.setState(prevState => {
                prevState.member = this.state.member
                return prevState
            })
        }
        if (prevState.checkedOutBooks.length !== this.state.checkedOutBooks.length) {
            this.setState(prevState => {
                prevState.checkedOutBooks = this.state.checkedOutBooks
                return prevState
            })
        }
    }


    deleteMember = (memberId) => {
        fetch(`http://localhost:8080/api/members/${memberId}`, {
            method: "DELETE"
        })
            .then(response => response.json())
    }

    deleteBookCopy = (bookId) => {
        fetch(`http://localhost:8080/api/book-copies/${bookId}`, {
            method: "DELETE"
        })
            .then(response => response.json())
    }

    returnBook = (bookId) => {
        fetch(`http://localhost:8080/api/members/${this.props.match.params.memberId}/return/${bookId}`, {
            method: "POST"
        })
            .then(response => response.json())
    }

    render() {
        return(
            <div className="container">
                <div>
                    {this.props.match.path.toString().includes("admin") &&
                     <button className="btn btn-primary btn-sm float-right"
                         onClick={() => {
                         this.changeEditing()
                         console.log(this.state.editing)}
                     }>
                         Edit
                     </button>

                    }
                    {this.props.match.path.toString().includes("admin") &&
                        <button className="btn btn-primary btn-sm"
                                onClick={() => {
                                    this.updateMember(this.state.librarian)
                                }}>
                            Update Profile
                        </button>
                    }

                    <h1>MEMBER PROFILE PAGE</h1>

                    {this.state.editing === false &&
                     <div>
                         <h3>Username</h3>
                         <h4 className="form-control">
                             {this.state.member.username}
                         </h4>
                         <h3>Password</h3>
                         <h4 className="form-control">
                             {this.state.member.password}
                         </h4>
                         <h3>Email</h3>
                         <h4 className="form-control">
                             {this.state.member.email}
                         </h4>
                         <h3>Date of Birth</h3>
                         <h4 className="form-control">
                             {this.state.member.dateOfBirth}
                         </h4>
                         <h3>First Name</h3>
                         <h4 className="form-control">
                             {this.state.member.firstName}
                         </h4>
                         <h3>Last Name</h3>
                         <h4 className="form-control">
                             {this.state.member.lastName}
                         </h4>
                         {this.state.member.sponsoredBy &&
                          <span>
                        <h3>Sponsored By</h3>
                        <h4 className="form-control">
                            {this.state.member.sponsoredBy}
                            {console.log("HERE",this.state.sponsorInfo)}
                        </h4>
                     </span>

                         }
                         <h3>Library Card Expiration Date</h3>
                         <h4 className="form-control">
                             {this.state.libraryCardExpirationDate}
                         </h4>
                     </div>
                    }


                    {this.state.editing === true &&
                     <div>
                         <h3>Username</h3>
                         <input className="form-control" placeholder="Username" onChange={(e) => {
                             const newUsername = e.target.value;
                             this.setState({
                                               member: {
                                                   ...this.state.member, username: newUsername
                                               }
                                           }
                             )

                         }
                         }
                                value={this.state.member.username}/>
                         {/*<h4 className="form-control">*/}
                         {/*    {this.state.member.username}*/}
                         {/*</h4>*/}
                         <h3>Password</h3>
                         <input className="form-control" placeholder="Password" type="password" onChange={(e) => {
                             const newPassword = e.target.value;
                             this.setState({
                                               member: {
                                                   ...this.state.member, password: newPassword
                                               }
                                           }
                             )

                         }
                         }
                                value={this.state.member.password}/>
                         {/*<h4 className="form-control">*/}
                         {/*    {this.state.member.password}*/}
                         {/*</h4>*/}
                         <h3>Email</h3>
                         <input className="form-control" placeholder="Email" onChange={(e) => {
                             const newEmail = e.target.value;
                             this.setState({
                                               member: {
                                                   ...this.state.member, email: newEmail
                                               }
                                           }
                             )

                         }
                         }
                                value={this.state.member.email}/>
                         {/*<h4 className="form-control">*/}
                         {/*    {this.state.member.email}*/}
                         {/*</h4>*/}
                         <h3>Date of Birth</h3>
                         <input className="form-control" type="date" onChange={(e) => {
                             const newDOB = e.target.value;
                             this.setState({
                                               member: {
                                                   ...this.state.member, dateOfBirth: newDOB
                                               }
                                           }
                             )

                         }
                         }
                                value={this.state.member.dateOfBirth}/>
                         {/*<h4 className="form-control">*/}
                         {/*    {this.state.member.dateOfBirth}*/}
                         {/*</h4>*/}


                         <h3>First Name</h3>
                         <input className="form-control" placeholder="First Name" onChange={(e) => {
                             const firstName = e.target.value;
                             this.setState({
                                               member: {
                                                   ...this.state.member, firstName: firstName
                                               }
                                           }
                             )

                         }
                         }
                                value={this.state.member.firstName}/>
                         {/*<h4 className="form-control">*/}
                         {/*    {this.state.member.firstName}*/}
                         {/*</h4>*/}
                         <h3>Last Name</h3>
                         {/*<h4 className="form-control">*/}
                         {/*    {this.state.member.lastName}*/}
                         {/*</h4>*/}
                         <input className="form-control" placeholder="Last Name" onChange={(e) => {
                             const lastName = e.target.value;
                             this.setState({
                                               member: {
                                                   ...this.state.member, lastName: lastName
                                               }
                                           }
                             )

                         }
                         }
                                value={this.state.member.lastName}/>


                         {this.state.member.sponsoredBy &&
                          <span>
                        <h3>Sponsored By</h3>
                        <h4 className="form-control">
                            {this.state.member.sponsoredBy}
                            {console.log("HERE",this.state.sponsorInfo)}
                        </h4>
                     </span>



                         }







                         <h3>Library Card Expiration Date</h3>
                         <h4 className="form-control">
                             {this.state.libraryCardExpirationDate}
                         </h4>
                     </div>
                    }

                    {this.state.sponsoredList
                     &&
                     <h3>
                         Sponsored Members:
                     </h3>
                    }

                    {this.state.sponsoredList

                     && this.state.sponsoredList.map((users, index) =>

                         <span>
                             {/*<li>*/}
                                 <h4 className="form-control">
                                    {users.firstName} {users.lastName}
                                 </h4>
                             {/*</li>*/}
                         </span>
                    )}

                    <br/>
                    <h3> Currently Rented Books by ID </h3>
                    {this.state.checkedOutBooks && this.state.checkedOutBooks.length > 0 &&
                     this.state.checkedOutBooks.map((book, index) =>
                        <li key={index} className="list-group-item">
                            <span>
                                Rented Book Id: {book[0].id}
                                <br/>
                                Rented Book Name: {book[1]}
                                {this.props.match.path.toString().includes("user-management") &&
                                    <button className="btn btn-primary btn-sm float-right" onClick={() => {
                                        this.deleteBookCopy(book[0].id)
                                    }}>
                                        <FontAwesomeIcon icon={faTrash} />
                                    </button>
                                }
                                {this.props.match.path.toString().includes("user-management") &&
                                 <button className="btn btn-primary btn-sm float-right" onClick={() => {
                                     this.returnBook(book[0].id)
                                 }}>
                                     Return Book

                                 </button>
                                }
                            </span>
                        </li>
                     )
                    }
                    <br/>
                    {this.props.match.path.toString().includes("admin") &&
                        <Link className="btn btn-primary btn-block" to={"/userAdmin"}
                                onClick={ () => {
                                    this.deleteMember(this.props.match.params.memberId)
                                }}
                        >
                            Delete
                        </Link>

                    }
                    {this.props.match.path.toString().includes("librarian") &&
                     <Link className="btn btn-primary btn-block" to={"/librarian"}
                           onClick={ () => {
                               this.deleteMember(this.props.match.params.memberId)
                           }}
                     >
                         Delete
                     </Link>

                    }


                </div>
            </div>
        )
    }
}
