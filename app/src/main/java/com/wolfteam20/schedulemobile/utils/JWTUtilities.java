package com.wolfteam20.schedulemobile.utils;

import java.text.MessageFormat;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Created by Efrain Bastidas on 1/6/2018.
 */

public class JWTUtilities {

    private String mToken;
    private Claims mClaims;

    public JWTUtilities(String token) {
        mToken = token;
        mClaims = getTokenClaims(token);
    }

    public String getUsername() {
        return mClaims.get(Constants.CLAIM_NAME, String.class);
    }

    public int getCedula() {
        return Integer.parseInt(mClaims.get(Constants.CLAIM_NAMEIDENTIFIER, String.class));
    }

    public String getFullName() {
        String nombre = mClaims.get(Constants.CLAIM_GIVENNAME, String.class);
        String apellido = mClaims.get(Constants.CLAIM_SURNAME, String.class);
        return MessageFormat.format("{0} {1}", nombre, apellido);
    }

    private Claims getTokenClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Constants.SECRET.getBytes())
                .parseClaimsJws(token).getBody();
    }

    public boolean isUserAdmin() {
        String role = mClaims.get(Constants.ROLE_CLAIM, String.class);
        return role.equals(Constants.ROLE_ADMIN);
    }
}

//JWT jwt = new JWT();
//        String claim = jwt.getClaim(ROLE_ADMIN).asString();


//        return objeto == null;
// Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
// be used to validate and process the JWT.
// The specific validation requirements for a JWT are context dependent, however,
// it typically advisable to require a (reasonable) expiration time, a trusted issuer, and
// and audience that identifies your system as the intended recipient.
// If the JWT is encrypted too, you need only provide a decryption key or
// decryption key resolver to the builder.

//    JwtConsumer jwtConsumer = new JwtConsumerBuilder()
//                //.setSkipAllValidators()
//                //.setDisableRequireSignature()
//                //.setSkipSignatureVerification()
//                //.setRequireExpirationTime() // the JWT must have an expiration time
//                //.setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
//                .setRequireSubject() // the JWT must have a subject claim
//                .setExpectedIssuer("ExampleIssuer") // whom the JWT needs to have been issued by
//                .setExpectedAudience("ExampleAudience") // to whom the JWT is intended for
//                .setVerificationKey(new HmacKey(secret.getBytes()))
//                .setRelaxVerificationKeyValidation() // allow shorter HMAC keys when used w/ HSxxx algs
//
//                //.setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
//                //        new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, // which is only RS256 here
//                //               AlgorithmIdentifiers.HMAC_SHA256))
//                .build(); // create the JwtConsumer instance
//        try {
//            //  Validate the JWT and process it to the Claims
//            JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
//            //jwtClaims.
//            System.out.println("JWT validation succeeded! " + jwtClaims);
//            return true;
//        } catch (InvalidJwtException e){
//            System.out.println("Invalid jwt!" + e.getMessage());
//        }