import React from 'react'
import {BrowserRouter, Route, Link} from "react-router-dom";
import BookSearch from "./BookSearch";
import BookDetails from "./BookDetails";
import LoginPage from "../login/LoginPage";
import Registration from "../registration/Registration";
import MemberPage from "../member/MemberPage"
import LibrarianPage from "../librarian/LibrarianPage"
import UserAdminPage from "../userAdmin/UserAdminPage"
import MemberProfilePage from "../profile/MemberProfilePage"
import MemberSearch from "../member/MemberSearch";
import LibrarianSearch from "../librarian/LibrarianSearch";


import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faSearch} from '@fortawesome/free-solid-svg-icons'
import {faBook} from "@fortawesome/free-solid-svg-icons";
import {faHome} from "@fortawesome/free-solid-svg-icons";
import HomePage from "../home-page/HomePage";
import LibrarianProfilePage from "../profile/LibrarianProfilePage";
import UserAdminRegistration from "../registration/UserAdminRegistration";
import CreateBookCopy from "./CreateBookCopy";
import AuthorDetail from "../author/AuthorDetail";
import ViewAllMembers from "../librarian/ViewAllMembers";
import AllUsersView from "../userAdmin/AllUsersView";
import BookCopyPage from "./BookCopyPage";


export class BookContainer extends React.Component {

    render () {
        return (
            <div className="container row">

                <BrowserRouter>
                    <div className="col">
                        <Link className="btn btn-primary float-left " to={`/`}>
                            <FontAwesomeIcon icon={faHome}/>
                        </Link>
                        <br/>
                        <Route
                            path="/"
                            exact={true}
                            component={HomePage}
                        />
                        <Route
                            path="/book-search/"
                            exact={true}
                            component={BookSearch}
                        />

                        <Route
                            path="/book-search/:bookSearchedFor"
                            exact={true}
                            component={BookSearch}
                        />

                        <Route
                            path="/book-search/book/:bookId"
                            exact={true}
                            component={BookDetails}
                        />

                        <Route
                            path="/login"
                            exact={true}
                            component={LoginPage}
                        />

                        <Route
                            path="/registration"
                            exact={true}
                            component={Registration}
                        />

                        <Route
                            path="/author-details/:authorLastName/:authorId"
                            exact={true}
                            component={AuthorDetail}
                        />

                        <Route
                            path="/user-management/admin/registration"
                            exact={true}
                            component={UserAdminRegistration}
                        />

                        <Route
                            path="/user-management/librarian/registration"
                            exact={true}
                            component={UserAdminRegistration}
                        />

                        <Route
                            path="/member"
                            exact={true}
                            component={MemberPage}
                        />

                        <Route
                            path="/librarian"
                            exact={true}
                            component={LibrarianPage}
                        />

                        <Route
                            path="/userAdmin"
                            exact={true}
                            component={UserAdminPage}
                        />

                        <Route
                            path="/member-profile"
                            exact={true}
                            component={MemberProfilePage}
                        />

                        <Route
                            path="/member-profile/:memberId"
                            exact={true}
                            component={MemberProfilePage}
                        />

                        <Route
                            path="/user-management/admin/member-profile"
                            exact={true}
                            component={MemberProfilePage}
                        />

                        <Route
                            path="/user-management/admin/member-profile/:memberId"
                            exact={true}
                            component={MemberProfilePage}
                        />

                        <Route
                            path="/user-management/librarian/member-profile"
                            exact={true}
                            component={MemberProfilePage}
                        />

                        <Route
                            path="/user-management/librarian/member-profile/:memberId"
                            exact={true}
                            component={MemberProfilePage}
                        />

                        <Route
                            path="/librarian-profile"
                            exact={true}
                            component={LibrarianProfilePage}
                        />

                        <Route
                            path="/librarian-profile/:librarianId"
                            exact={true}
                            component={LibrarianProfilePage}
                        />

                        <Route
                            path="/user-management/admin/librarian-profile"
                            exact={true}
                            component={LibrarianProfilePage}
                        />

                        <Route
                            path="/user-management/admin/librarian-profile/:librarianId"
                            exact={true}
                            component={LibrarianProfilePage}
                        />

                        <Route
                        path="/member-search"
                        exact={true}
                        component={MemberSearch}/>

                        <Route
                            path="/member-search/:memberSearchedFor"
                            exact={true}
                            component={MemberSearch}/>

                        <Route
                            path="/librarian-search"
                            exact={true}
                            component={LibrarianSearch}/>

                        <Route
                            path="/librarian-search/:librarianSearchedFor"
                            exact={true}
                            component={LibrarianSearch}/>



                        <Route
                            path="/user-management/admin/member-search"
                            exact={true}
                            component={MemberSearch}/>

                        <Route
                            path="/user-management/admin/member-search/:memberSearchedFor"
                            exact={true}
                            component={MemberSearch}/>


                        <Route
                            path="/user-management/librarian/member-search"
                            exact={true}
                            component={MemberSearch}/>

                        <Route
                            path="/user-management/librarian/member-search/:memberSearchedFor"
                            exact={true}
                            component={MemberSearch}/>

                        <Route
                            path="/user-management/admin/librarian-search"
                            exact={true}
                            component={LibrarianSearch}/>

                        <Route
                            path="/user-management/admin/librarian-search/:librarianSearchedFor"
                            exact={true}
                            component={LibrarianSearch}/>

                        <Route
                            path="/user-management/admin/all-users"
                            exact={true}
                            component={AllUsersView}/>

                        <Route
                            path="/create-book"
                            exact={true}
                            component={CreateBookCopy}/>

                        <Route
                            path="/librarian/view-all-members"
                            exact={true}
                            component={ViewAllMembers}/>

                        <Route
                            path="/member/view-all-members"
                            exact={true}
                            component={ViewAllMembers}/>

                        <Route
                            path="/book-copy-page"
                            exact={true}
                            component={BookCopyPage}/>
                    </div>
                </BrowserRouter>
            </div>
        )
    }
}
