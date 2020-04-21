# VCBuilder
Simple Verifiable Credential generator in Java

The generated VC asserts that a valid identity (fiscal code) associated to a given ethereum address exists

The VC is implemented as a signed Java Web Token (JWT)

## Generated VC example

### input
* address: 0xcf87ce923fe20968F491556Df7833C948400d68a
* owner's fiscal code: BLRVQM40H43L753F
* issuer's private key (from config.txt file): 0x7feed7d6a6239c3d3f698034fab88828bdfc07e3e1bde5516b2e583abfe5cc43

### output
```
{
  "payload" : {
    "sub" : "0xcf87ce923fe20968F491556Df7833C948400d68a",
    "csu" : {
      "ownedBy" : "BLRVQM40H43L753F"
    },
    "iss" : "0x0527c70071b5246604982342b1e9a7e1eaf761ff"
  },
  "signature" : "0x8272d3b5041af477f1fc8e6cb8cdd12d45ac60f31a9689e593c84e9c52d02c1904dc8963a4095e891de21c07510b3f92d9218a1e880e0b39971ecca6fe236e811c",
  "header" : {
    "typ" : "JWT",
    "alg" : "ES256K-R"
  }
}
```
