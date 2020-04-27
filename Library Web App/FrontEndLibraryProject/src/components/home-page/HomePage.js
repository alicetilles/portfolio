import React from "react";
import {BrowserRouter, Link} from "react-router-dom";
import HomePageStyle from "./HomePage.css"

export default class HomePage extends React.Component {
    state = {

    }

    componentDidMount() {
    }

    componentDidUpdate(prevProps, prevState, snapshot) {

    }


    render() {
        return(
            <div className="container">
                <div className="justify-content-center">
                    <h1>
                        Welcome to The Online-Brary!
                    </h1>
                    <br/>
                    <Link className="btn btn-primary btn-block " to={`/login`}>
                        Login
                    </Link>
                    <br/>

                    <Link className="btn btn-primary btn-block " to={`/registration/`}>
                        Registration
                    </Link>
                    <br/>
                    <Link className="btn btn-primary btn-block " to={`/book-search/`}>
                        Book Search
                    </Link>
                    <br/>
                    {/*<Link className="btn btn-primary btn-block " to={`/member-search`}>*/}
                    {/*    Search Members*/}
                    {/*</Link>*/}

                </div>

            </div>
        )
    }
}
