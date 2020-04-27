import React from "react";
import {Link} from "react-router-dom";

export default class AuthorDetails extends React.Component {

    componentDidMount() {
        const authorId = this.props.match.params.authorId;
        const authorLastName = this.props.match.params.authorLastName;
        fetch(`http://localhost:8080/api/authors/${authorId}`)
            .then(response => response.json())
            .then(result => this.setState({
                                              author: result
                                          }))
        fetch(`http://localhost:8080/api/authors/${authorLastName}/books`)
            .then(response => response.json())
            .then(result => this.setState({
                                              booksWrittenByAuthor: result
                                          }))
    }

    state = {
        author: {},
        booksWrittenByAuthor:[]
    }

    render() {
        return(
            <div>
                {this.state.author &&
                    <h1>Author Name: {this.state.author.firstName} {this.state.author.lastName}</h1>
                }
                {this.state.author.dateOfBirth !== null &&
                    <h1>Date of Birth: {this.state.author.dateOfBirth}</h1>
                }
                {this.state.author.dateOfDeath !== null &&
                 <h1>Date of Death: {this.state.author.dateOfDeath}</h1>
                }
                {this.state.author.hometown !== null &&
                 <h1>Author's hometown: {this.state.author.hometown}</h1>
                }
                {this.state.booksWrittenByAuthor &&
                 this.state.booksWrittenByAuthor.map((book, index) =>
                                          <li key={index} className="list-group-item">
                                              <Link to={`/book-search/book/${book.id}`}>
                                                  {book.title}
                                              </Link>
                                          </li>
                 )}
            </div>
        )
    }
}
