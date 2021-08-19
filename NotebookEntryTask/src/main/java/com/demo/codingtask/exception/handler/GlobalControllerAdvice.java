package com.demo.codingtask.exception.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.demo.codingtask.dto.DataDto;
import com.demo.codingtask.dto.ErrorDto;
import com.demo.codingtask.dto.ResponseDto;
import com.demo.codingtask.utility.Constants;
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalControllerAdvice {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalControllerAdvice.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = ApplicationException.class)
	public @ResponseBody ResponseDto handleException(ApplicationException e) {
		logger.error("Global Application Exception", e);
		List dataItems = new ArrayList<>();
		if (!e.getErrorCode().isEmpty() && !e.getMessage().isEmpty()) {
			dataItems.add(new ErrorDto(e.getErrorCode(), e.getMessage()));
		}
		if (e.getWsUuid() != null) {
			logger.info(
					"Global Application Exception with External System UUID: "
							+ e.getWsUuid());
			dataItems.add(e.getWsUuid());
		} else {
			String uuid = UUID.randomUUID().toString();
			logger.info("Global Application Exception with generated UUID: "
					+ uuid);
			dataItems.add(uuid);
		}

		DataDto<ErrorDto> data = new DataDto<>(dataItems);
		ResponseDto errResp = new ResponseDto(Constants.REST_STATUS_ERROR, data,
				Constants.APP_EXP, e.getErrorCode());
		return errResp;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public @ResponseBody ResponseDto handleException(Exception e) {
		logger.error("Global Exception", e);
		ResponseDto errResp = new ResponseDto(Constants.REST_STATUS_ERROR, null,
				Constants.APP_EXP, null);
		return errResp;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public @ResponseBody ResponseDto handleException(
			MissingServletRequestParameterException e) {
		logger.error("Global Validation Exception", e);
		ResponseDto errResp = new ResponseDto(Constants.REST_STATUS_ERROR, null,
				e.getMessage(), null);
		return errResp;
	}
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public @ResponseBody ResponseDto handleException(
			MethodArgumentNotValidException e) {
		logger.error("Global Validation Exception", e);
		List<ErrorDto> dataItems = e.getBindingResult().getFieldErrors()
				.stream()
				.map(a -> new ErrorDto(a.getField(), a.getDefaultMessage()))
				.collect(Collectors.toCollection(ArrayList::new));
		DataDto<ErrorDto> data = new DataDto<>(dataItems);
		ResponseDto errResp = new ResponseDto(Constants.REST_STATUS_FAIL, data,
				null, null);
		return errResp;
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = {BindException.class})
	public @ResponseBody ResponseDto handleException(BindException e) {
		logger.error("Global Validation Exception", e);
		List<ErrorDto> dataItems = e.getBindingResult().getFieldErrors()
				.stream()
				.map(a -> new ErrorDto(a.getField(), a.getDefaultMessage()))
				.collect(Collectors.toCollection(ArrayList::new));
		DataDto<ErrorDto> data = new DataDto<>(dataItems);
		ResponseDto errResp = new ResponseDto(Constants.REST_STATUS_FAIL, data,
				null, null);
		return errResp;
	}

	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
	public @ResponseBody ResponseDto handleException(
			HttpMediaTypeNotSupportedException e) {
		logger.error("HttpMediaTypeNotSupportedException", e);
		StringBuilder builder = new StringBuilder();
		builder.append(e.getContentType());
		builder.append(
				" media type is not supported. Supported media types are ");
		e.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
		ResponseDto errResp = new ResponseDto(Constants.REST_STATUS_ERROR, null,
				builder.substring(0, builder.length() - 2), null);
		return errResp;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = HttpMessageNotWritableException.class)
	public @ResponseBody ResponseDto handleException(
			HttpMessageNotWritableException e) {
		logger.error("HttpMessageNotWritableException", e);
		String error = "Error writing JSON output";
		ResponseDto errResp = new ResponseDto(Constants.REST_STATUS_ERROR, null,
				error, null);
		return errResp;
	}

	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public @ResponseBody ResponseDto handleException(
			HttpRequestMethodNotSupportedException e) {
		logger.error("HttpRequestMethodNotSupportedException", e);
		StringBuilder builder = new StringBuilder();
		builder.append(e.getMethod());
		builder.append(
				" media type is not supported. Supported http methods are ");
		e.getSupportedHttpMethods()
				.forEach(t -> builder.append(t).append(", "));
		ResponseDto errResp = new ResponseDto(Constants.REST_STATUS_ERROR, null,
				builder.substring(0, builder.length() - 2), null);
		return errResp;
	}
}
