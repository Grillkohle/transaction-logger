# Transaction Logger Service

This service logs transactions and provides some statistics about the transactions that have been logged within the last 60 seconds.

The API is defined in Swagger format, copy+paste into http://editor.swagger.io/ for better reading.

Notes about the implementation: 
* The Error definition wasn't implemented.
* The tests were written before switching to the thread based model to get the GET into constant run-time. I didn't adjust them to run with the thread implementation - something for the future. Currently, all existing tests are marked with `@Ignore`
* Also, an integration test which tests all components is missing.

## Usage

`mvn clean install spring-boot:run`