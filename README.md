GloboNetwork-Client
==================

Java client for GloboNetwork.

https://github.com/globocom/globonetwork-client

## To release a new version

    mvn clean release:prepare -Dgpg.passphrase='XXX'
    mvn release:perform -Dgpg.passphrase='XXX'

##  With error: Inappropriate ioctl for device
gpg version > 2.0 in MAC

https://github.com/keybase/keybase-issues/issues/1712

just type before mvn install

```sh
export GPG_TTY=$(tty)
```