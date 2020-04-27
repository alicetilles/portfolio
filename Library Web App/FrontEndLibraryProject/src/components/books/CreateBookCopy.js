import React from 'react'

export default class CreateBookCopy extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        fetch(`http://localhost:8080/api/books`)
            .then(results => results.json())
            .then(result => this.setState({
                                              allBooksInDB: result
                                          }))

    }

    state = {
        edition: '',
        isAvailable: '',
        fileSizeMb: '',
        fileType: '',
        narratedBy: '',
        dtype: 'audiobooks',
        numMinutes: '',
        currentCondition: '',
        numPages: '',
        book_id: '',
        allBooksInDB: [],
        bookToCopy: {}

    };

    createBook = (bookCopy) => {
        {this.state.dtype ===  'hard_copy' &&
         fetch(`http://localhost:8080/api/hard-copy-books/${this.state.book_id}`, {
             method: 'POST',
             body: JSON.stringify(bookCopy),
             headers: {
                 'content-type': 'application/json'
             },
         }).then(response => response.json())
             .then(response => console.log(response))
        }
        {this.state.dtype === 'audiobooks' &&
         fetch(`http://localhost:8080/api/audio-books/${this.state.book_id}`, {
             method: 'POST',
             body: JSON.stringify(bookCopy),
             headers: {
                 'content-type': 'application/json'
             },
         }).then(response => response.json())
             .then(response => console.log(response))
        }
    }



    render() {
        return(
            <div className="container">

                <h4 >
                    Book To Copy
                </h4>

                <select id="bookType" className="custom-select" onChange={(event => {
                    const book_id = event.target.value;
                    this.setState({
                                      book_id : book_id
                                  })
                })}
                        value={this.state.book_id}>
                    {this.state.allBooksInDB &&
                     this.state.allBooksInDB.map(book =>
                         <option value={book.id} key={book.id}>
                             {book.title}
                         </option>
                     )

                    }
                </select>

                {/*<input placeholder="Book ID" type="text" className="input-group"*/}
                {/*       onChange={ (e) =>*/}
                {/*           this.setState({*/}
                {/*                             book_id: e.target.value*/}
                {/*                         })*/}
                {/*       }/>*/}
                {/*<br/>*/}









                <h4 >
                    Is Available?
                </h4>
                <input placeholder="Is Available?" type="text" className="input-group"
                       onChange={ (e) =>
                           this.setState({
                                             is_available: e.target.value
                                         })
                       }/>
                <br/>


                <h4 >
                    Book Type
                </h4>
                <select id="bookType" className="custom-select" onChange={(event => {
                    const newUserType = event.target.value;
                    this.setState({
                                      dtype : newUserType
                                  })
                })}
                        value={this.state.dtype}>
                    <option value="hard_copy">
                        Hard Copy
                    </option>
                    <option value="audiobooks">
                        Audiobook
                    </option>
                </select>
                <br/>




                {/*<br/>*/}
                {/*{this.state.dtype === 'audiobooks' &&*/}
                {/* <div>*/}

                {/*    <h4>*/}
                {/*        File Size MD*/}
                {/*    </h4>*/}
                {/*     <input placeholder="File Size MD" type="text" className="input-group"*/}
                {/*            onChange={ (e) =>*/}
                {/*                this.setState({*/}
                {/*                                  fileSizeMb: e.target.value*/}
                {/*                              })*/}
                {/*            }/>*/}
                {/*    <h4>*/}
                {/*         File Type*/}
                {/*    </h4>*/}
                {/*     <input placeholder="File Type" type="text" className="input-group"*/}
                {/*            onChange={ (e) =>*/}
                {/*                this.setState({*/}
                {/*                                  fileType: e.target.value*/}
                {/*                              })*/}
                {/*            }/>*/}
                {/*    <h4>*/}
                {/*        Narrated By*/}
                {/*    </h4>*/}
                {/*     <input placeholder="Narrated By" type="text" className="input-group"*/}
                {/*            onChange={ (e) =>*/}
                {/*                this.setState({*/}
                {/*                                  narratedBy: e.target.value*/}
                {/*                              })*/}
                {/*            }/>*/}
                {/*    <h4>*/}
                {/*        Length*/}
                {/*    </h4>*/}
                {/*     <input placeholder="Number of Minutes" type="number" className="input-group"*/}
                {/*            onChange={ (e) =>*/}
                {/*                this.setState({*/}
                {/*                                  numMinutes: e.target.value*/}
                {/*                              })*/}
                {/*            }/>*/}
                {/* </div>*/}
                {/*}*/}




                {/*{this.state.dtype === 'hard_copy' &&*/}
                {/* <div>*/}

                {/*     <h4>*/}
                {/*         Edition*/}
                {/*     </h4>*/}
                {/*     <input placeholder="Edition" type="text" className="input-group"*/}
                {/*            onChange={ (e) =>*/}
                {/*                this.setState({*/}
                {/*                                  edition: e.target.value*/}
                {/*                              })*/}
                {/*            }/>*/}
                {/*     <h4>*/}
                {/*         Current Condition*/}
                {/*     </h4>*/}
                {/*     <select id="currentCondition" className="custom-select" onChange={(event => {*/}
                {/*         const currentCondition = event.target.value;*/}
                {/*         this.setState({*/}
                {/*                           currentCondition : currentCondition*/}
                {/*                       })*/}
                {/*     })}*/}
                {/*             value={this.state.currentCondition}>*/}
                {/*         <option value="NEW">*/}
                {/*             New*/}
                {/*         </option>*/}
                {/*         <option value="GOOD">*/}
                {/*             Good*/}
                {/*         </option>*/}
                {/*         <option value="ACCEPTABLE">*/}
                {/*             Acceptable*/}
                {/*         </option>*/}
                {/*         <option value="WORN">*/}
                {/*             Worn*/}
                {/*         </option>*/}
                {/*     </select>*/}
                {/*     <h4>*/}
                {/*         Length*/}
                {/*     </h4>*/}
                {/*     <input placeholder="Length" type="number" className="input-group"*/}
                {/*            onChange={ (e) =>*/}
                {/*                this.setState({*/}
                {/*                                  numPages: e.target.value*/}
                {/*                              })*/}
                {/*            }/>*/}
                {/* </div>*/}
                {/*}*/}
                <br/>

                <button className="btn btn-primary btn-block"
                        onClick={ () => {
                            let book = {
                                book_id: this.state.bookToCopy,
                                isAvailable: this.state.isAvailable,
                                dtype: this.state.dtype,
                            };
                            console.log(book)
                            this.createBook(book)
                        }}
                >
                    Create Book Copy
                </button>
            </div>
        )
    }
}
