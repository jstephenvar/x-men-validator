package com.meli.challenge.xmenvalidator.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.DecryptionFailureException;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InternalServiceErrorException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.InvalidParameterException;

import static com.meli.challenge.xmenvalidator.general.Constants.ERROR_SECRET_DECRYPT;
import static com.meli.challenge.xmenvalidator.general.Constants.ERROR_SECRET_INVALID_PARAM;
import static com.meli.challenge.xmenvalidator.general.Constants.ERROR_SECRET_REQUEST;
import static com.meli.challenge.xmenvalidator.general.Constants.ERROR_SECRET_RESOURCE_NOT_FOUND;
import static com.meli.challenge.xmenvalidator.general.Constants.ERROR_SECRET_SERVICE_FAIL;

@Log4j2
@Configuration
@EnableDynamoDBRepositories
        (basePackages = "com.meli.challenge.xmenvalidator.repository")
public class DynamoConfig {
    
    
    private static final String KEY = "accesskey";
    private static final String SECRET = "secretkey";
    @Value("${amazon.aws.dynamo}")
    private String secretName;
    @Value("${amazon.aws.region}")
    private String region;
    
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(amazonAWSCredentials()))
                .withRegion(Regions.US_EAST_1)
                .build();
    }
    
    @Bean
    public AWSCredentials amazonAWSCredentials() {
        JsonObject jsonObject = getSecret();
        return new BasicAWSCredentials(
                jsonObject.get(KEY).getAsString(), jsonObject.get(SECRET).getAsString());
    }
    
    /**
     * Get secret keys for connection to dynamo from AWS secret.
     *
     * @return JSONObject keys
     */
    private JsonObject getSecret() {
        
        // Create a Secrets Manager client
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();
        
        String secret = "";
        
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(this.secretName);
        GetSecretValueResult getSecretValueResult = null;
        
        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException e) {
            log.error(ERROR_SECRET_DECRYPT, e);
            throw e;
        } catch (InternalServiceErrorException e) {
            log.error(ERROR_SECRET_SERVICE_FAIL, e);
            throw e;
        } catch (InvalidParameterException e) {
            log.error(ERROR_SECRET_INVALID_PARAM, e);
            throw e;
        } catch (InvalidRequestException e) {
            log.error(ERROR_SECRET_REQUEST, e);
            throw e;
        } catch (ResourceNotFoundException e) {
            log.error(ERROR_SECRET_RESOURCE_NOT_FOUND, e);
            throw e;
        }
        
        // Decrypts secret using the associated KMS CMK.
        // Depending on whether the secret is a string or binary, one of these fields will be populated.
        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
        }
        
        return new Gson().fromJson(secret, JsonObject.class);
    }
    
}
