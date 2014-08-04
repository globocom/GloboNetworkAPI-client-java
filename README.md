GloboNetwork-Client
==================

Java client for GloboNetwork.

https://github.com/globocom/globonetwork-client

## To release a new version

    mvn clean release:prepare -Dgpg.passphrase='XXX'
    mvn release:perform -Dgpg.passphrase='XXX'

