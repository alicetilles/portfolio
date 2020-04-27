import React from "react";
import {Link} from "react-router-dom";

export default class LibrarianPage extends React.Component {
    render() {
        return(
            <div>
                <h1>Librarian</h1>
                <Link className="btn btn-primary btn-block " to={`/user-management/librarian/registration`}>
                    Create Member
                </Link>
                <Link className="btn btn-primary btn-block " to={`/user-management/librarian/member-search`}>
                    Search Members
                </Link>
                <Link className="btn btn-primary btn-block " to={`/librarian/view-all-members`}>
                    View All Members
                </Link>
                <Link className="btn btn-primary btn-block " to={`/book-search/`}>
                    Book Search
                </Link>
                <Link className="btn btn-primary btn-block " to={`/book-copy-page/`}>
                    Book Copy List
                </Link>
                <Link className="btn btn-primary btn-block " to={`/create-book`}>
                    Create Book Copy
                </Link>
            </div>
        )
    }

}
