import React from 'react';
import {Link} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash} from "@fortawesome/free-solid-svg-icons";

export default class BookCopyPage extends React.Component {

    state = {
        audioBooks: [],
        hardCopyBook: []

    }

    componentDidMount() {

        fetch(`http://localhost:8080/api/audio-books`)
            .then(response => response.json())
            .then(results => this.setState({
                                               audioBooks: results,
                                           }))
        fetch(`http://localhost:8080/api/hard-copy-books`)
            .then(response => response.json())
            .then(results => this.setState({
                                               hardCopyBook: results,
                                           }))
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevState.hardCopyBook!== this.state.hardCopyBook) {
            // this.setState(prevState => {
            //     prevState.hardCopyBook = this.state.hardCopyBook
            //     return prevState
            // })
            fetch(`http://localhost:8080/api/hard-copy-books`)
                .then(response => response.json())
                .then(results => this.setState({
                                                   hardCopyBook: results,
                                               }))
        }
    }

    deleteBookCopy = (bookId) => {
        fetch(`http://localhost:8080/api/book-copies/${bookId}`, {
            method: "DELETE"
        })
            .then(response => response.json())
    }

    render() {
        return(
            <div className="row">
                <ul className="list-group col">
                    <div>
                        <h1>
                            Audio Books
                        </h1>
                    {this.state.audioBooks
                     && this.state.audioBooks.map((book, index) =>
                        <span>
                            {book.available === false &&
                             <li key={index} className="list-group-item">
                                 <h4>
                                     Audio Book Id Number: {book.id}
                                     <br/>
                                     Available?: {book.available.toString()}
                                 </h4>
                                 <button className="btn btn-primary btn-sm" onClick={() => {
                                     this.deleteBookCopy(book.id)
                                 }}>
                                     <FontAwesomeIcon icon={faTrash} />
                                 </button>
                             </li>
                            }

                        </span>

                    )

                    }
                    </div>

                </ul>

                <ul className="list-group col">
                    <div>
                        <h1>
                            Hard Copy Books
                        </h1>
                        {this.state.hardCopyBook
                         && this.state.hardCopyBook.map((book, index) =>
                            <span>
                                {book.available === false &&
                                 <li key={index} className="list-group-item">
                                     <h4>
                                         Audio Book Id Number: {book.id}
                                         <br/>
                                         Available?: {book.available.toString()}
                                     </h4>
                                     <button className="btn btn-primary btn-sm" onClick={() => {
                                         this.deleteBookCopy(book.id)
                                     }}>
                                         <FontAwesomeIcon icon={faTrash} />
                                     </button>
                                 </li>
                                }
                            </span>

                        )

                        }
                    </div>

                </ul>
            </div>
        )
    }
}
