package com.datascope.core.services;

public interface IRestClient {
    <TResult> TResult post(Class<TResult> ofType, String path);
    <TResult> TResult post(Class<TResult> ofType, String path, Object params);
}
