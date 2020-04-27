import React from "react";
import {Link} from "react-router-dom";

export default class MemberPage extends React.Component {
    render() {
        return(
            <div>
                <h1>Member</h1>
                <Link className="btn btn-primary btn-block " to={`/book-search/`}>
                    Book Search
                </Link>
                <Link className="btn btn-primary btn-block " to={`/member-search`}>
                    Search Members
                </Link>
                <Link className="btn btn-primary btn-block " to={`/member/view-all-members`}>
                    View All Members
                </Link>
            </div>
        )
    }

}
