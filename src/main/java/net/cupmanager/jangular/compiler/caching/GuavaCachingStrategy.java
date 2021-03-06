package net.cupmanager.jangular.compiler.caching;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import net.cupmanager.jangular.compiler.CompiledTemplate;


import net.cupmanager.jangular.compiler.templateloader.NoSuchScopeFieldException;
import net.cupmanager.jangular.compiler.templateloader.TemplateLoaderException;
import net.cupmanager.jangular.exceptions.CompileExpressionException;
import net.cupmanager.jangular.exceptions.ControllerNotFoundException;
import net.cupmanager.jangular.exceptions.ParseException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GuavaCachingStrategy implements CachingStrategy {

	private Cache<String, CompiledTemplate> cache;

//	public GuavaCachingStrategy(Cache<String, CompiledTemplate> cache) {
//		this.cache = cache;
//	}

	public GuavaCachingStrategy(CacheBuilder builder) {
		cache = builder.build();
	}

	@Override
	public CompiledTemplate get(String templatePath, Callable<CompiledTemplate> compileFunctor) throws ControllerNotFoundException, ParseException, NoSuchScopeFieldException, CompileExpressionException, TemplateLoaderException {
		try {
			return cache.get(templatePath, compileFunctor);
		} catch (ExecutionException e) {
			Throwable cause = e.getCause();
			
			if( cause instanceof RuntimeException ){
				throw (RuntimeException)cause;
			} else if (cause instanceof ControllerNotFoundException ){
				throw (ControllerNotFoundException)cause;
			} else if (cause instanceof ParseException ){
				throw (ParseException)cause;
			} else if (cause instanceof NoSuchScopeFieldException ){
				throw (NoSuchScopeFieldException)cause;
			} else if (cause instanceof CompileExpressionException ){
				throw (CompileExpressionException)cause;
			} else if (cause instanceof TemplateLoaderException ){
				throw (TemplateLoaderException)cause;
			} else {
				throw new TemplateLoaderException(cause);
			}
		}
	}
}
