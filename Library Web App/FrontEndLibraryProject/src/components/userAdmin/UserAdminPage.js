import React from "react";
import {Link} from "react-router-dom";

export default class UserAdminPage extends React.Component {
    state = {

    }


    render() {
        return(
            <div>
                <h1>User Admin</h1>
                <Link className="btn btn-primary btn-block " to={`/user-management/admin/registration`}>
                    Create User (Member, Librarian, Administrator)
                </Link>
                <Link className="btn btn-primary btn-block " to={`/user-management/admin/member-search`}>
                    Search Members
                </Link>
                <Link className="btn btn-primary btn-block " to={`/user-management/admin/librarian-search/`}>
                    Search Librarians
                </Link>
                <Link className="btn btn-primary btn-block " to={`/user-management/admin/all-users`}>
                    View All Users
                </Link>
                <Link className="btn btn-primary btn-block " to={`/create-book`}>
                    Create Book Copy
                </Link>
                <Link className="btn btn-primary btn-block " to={`/book-copy-page/`}>
                    Book Copy List
                </Link>
                <Link className="btn btn-primary btn-block " to={`/book-search/`}>
                    Book Search
                </Link>
            </div>

        )
    }

}
