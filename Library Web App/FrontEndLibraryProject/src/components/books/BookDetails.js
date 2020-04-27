import React from "react";
import {Link} from "react-router-dom";


export default class BookDetails extends React.Component {

    componentDidMount() {
        const bookId = this.props.match.params.bookId;
            fetch(`https://www.googleapis.com/books/v1/volumes/${bookId}`)
            .then(response => response.json())
            .then(book => this.setState({
                                            book: book.volumeInfo,
                                            image: book.volumeInfo.imageLinks
                                        }))
            fetch(`http://localhost:8080/api/hard-copy-books/${bookId}`)
                .then(response => response.json())
                .then(result => this.setState({
                    hardCoverBook: result
                                              }))
            fetch(`http://localhost:8080/api/audio-books/${bookId}`)
                .then(response => response.json())
                .then(result => this.setState({
                                                  audioBooks: result
                                              }))
        fetch(`http://localhost:8080/api/books/${bookId}`)
            .then(response => response.json())
            // .then(result => console.log(result))
            .then(result => this.setState({
                                              bookDB: result
                                          }))
    }

    state = {
        book: {},
        bookDB:{},
        image: {},
        hardCoverBook: [],
        audioBooks: [],
        renterId: '',
        availableHardCoverBooks: [],
        availableAudioBooks: []
    }

    rentHardCopy = (memberId) => {
        fetch(`http://localhost:8080/api/members/${memberId}/check-out/${this.props.match.params.bookId}/hard-copy`, {
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
        }).then(response => response.json())
    }
    rentAudiobook = (memberId) => {
        fetch(`http://localhost:8080/api/members/${memberId}/check-out/${this.props.match.params.bookId}/audiobook-copy`, {
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
        }).then(response => response.json())
    }
    render() {
        return(
            <div>
                {this.state.bookDB.id === null &&
                 <div>
                    {this.state.book.title &&
                          <h1>{this.state.book.title}</h1>
                    }

                    {this.state.book.subtitle &&
                        <h2>{this.state.book.subtitle}</h2>
                    }

                    {this.state.image &&
                        <img src={this.state.image.smallThumbnail}/>
                    }

                    {this.state.book.authors &&
                        <h1>Author: {this.state.book.authors}</h1>
                    }
                    {this.state.book.publisher &&
                        <h1>Publisher: {this.state.book.publisher}</h1>
                    }
                    {this.state.book.pageCount &&
                        <h1>Page Count: {this.state.book.pageCount}</h1>
                    }
                 </div>
                }
                {this.state.bookDB.id !== null &&
                    <div>
                        {this.state.bookDB.title &&
                            <h1>Book Title: {this.state.bookDB.title}</h1>
                        }
                        {this.state.bookDB.thumbnailURL &&
                         <div>
                            <img src={this.state.bookDB.thumbnailURL}/>
                         </div>
                        }
                        {this.state.bookDB.author &&
                            <Link to={`/author-details/${this.state.bookDB.author.lastName}/${this.state.bookDB.author.id}`}>
                                <h1>Author Name: {this.state.bookDB.author.firstName} {this.state.bookDB.author.lastName}</h1>
                            </Link>

                        }
                        {this.state.bookDB.yearPublished &&
                         <h1>Year Published: {this.state.bookDB.yearPublished}</h1>
                        }
                    </div>
                }


                {this.state.hardCoverBook && this.state.hardCoverBook.length > 0 &&
                 <div>

                        <h3>Number of Hard Cover copies available: {this.state.hardCoverBook.length}</h3>
                         <h4 >
                             Renter (Member) Id:
                         </h4>
                         <input placeholder="Member Id" type="text" className="input-group"
                                onChange={ (e) =>
                                    this.setState({
                                                      renterId: e.target.value
                                                  })
                                }/>
                        <button className="btn btn-primary btn-sm" onClick={() => {
                            this.rentHardCopy(this.state.renterId)
                        }}>Rent Hard Copy</button>
                 </div>

                }
                {this.state.audioBooks && this.state.audioBooks.length > 0 &&
                    <div>
                        <h3>Number of Audiobook copies: {this.state.audioBooks.length}</h3>
                        <h4 >
                            Renter (Member) Id:
                        </h4>
                        <input placeholder="Member Id" type="text" className="input-group"
                               onChange={ (e) =>
                                   this.setState({
                                                     renterId: e.target.value
                                                 })
                               }/>
                        <button className="btn btn-primary btn-sm" onClick={() => {
                            this.rentAudiobook(this.state.renterId)
                        }}>Rent Audiobook Copy</button>
                    </div>
                }
            </div>
        )
    }
}
