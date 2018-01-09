package com.nekonekod.tagger.taggerserver.exception;

import com.alibaba.fastjson.JSONObject;
import com.nekonekod.tagger.taggerserver.util.AjaxMsg;
import com.nekonekod.tagger.taggerserver.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Exception for handling business logic failures<br/>
 * will be used by {@link com.nekonekod.tagger.taggerserver.web.controller.GlobalControllerExceptionHandler}
 * <p>
 */
public class BusiLogicException extends RuntimeException {
    private static Logger LOG = LoggerFactory.getLogger(BusiLogicException.class);

    protected AjaxMsg ajaxMsg;

    protected static final AjaxMsg defaultError = new AjaxMsg(0, "", "Unknown Server Error");

    protected static final String logMsg = "\n(DETAILED MESSAGE) ";


    /**
     * The ajaxError is the message container for the bussiness logic failure
     *
     * @param ajaxMsg
     */
    public BusiLogicException(AjaxMsg ajaxMsg, Throwable cause) {
        this(ajaxMsg, cause, true);
    }

    /**
     * Remained for sub-class to determine whether write the StackTrace or not <br/>
     * see {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}
     *
     * @param ajaxMsg
     * @param writableStackTrace
     */
    protected BusiLogicException(AjaxMsg ajaxMsg, Throwable cause, boolean writableStackTrace) {
        super((ajaxMsg == null ? defaultError : ajaxMsg).getMessage(), cause, true, writableStackTrace);
        this.ajaxMsg = ajaxMsg == null ? defaultError : ajaxMsg;
    }


    /**
     * Create a BusiLogicException with the default ajaxError <br/>
     * please use {@link #init} to set error messages
     *
     * @return
     */
    public static BusiLogicException create() {
        return new BusiLogicException(null, null);
    }

    public static BusiLogicException create(Exception e) {
        return new BusiLogicException(null, e);
    }

    public BusiLogicException init(int code) {
        return init(code, "", "");
    }

    public BusiLogicException init(String message, Object... args) {
        return init(0, "", message, args);
    }

    public BusiLogicException init(int code, String message, Object... args) {
        return init(code, "", message, args);
    }

    public BusiLogicException init(int code, String type, String message, Object... args) {
        this.ajaxMsg = new AjaxMsg(code, type, StringUtil.seqFormat(message, args));
        return this;
    }

    /**
     * Throws this exception
     */
    public <T> T raise() throws BusiLogicException {
        throw this;
    }

    /**
     * Throws this exception and log with the params
     *
     * @param logString
     * @param logData
     */
    public void raise(String logString, Object... logData) {
        LOG.info(ajaxMsg.getMessage() + logMsg + logString, logData);
        throw this;
    }

    /**
     * Throws this exception and log with the params
     *
     * @param logString
     * @param logData
     */
    public <T> T raiseJson(String logString, Object... logData) {
        for (int i = 0; i < logData.length; i++) {
            Object o1 = logData[i];
            if (o1 == null) {
                logData[i] = "null";
                continue;
            }
            if (o1 instanceof Throwable) {
                Throwable throwable = ((Throwable) o1);
                logData[i] = throwable.getMessage();
                this.addSuppressed(throwable);
                continue;
            }
            try {
                logData[i] = JSONObject.toJSONString(o1);
            } catch (Exception e) {
                LOG.warn("BusiLogicException.raiseObject parse logData error", e);
                logData[i] = "[ERROR]:" + e.getMessage();
            }
        }
        if (this.getClass().equals(BusiLogicException.class)) {
            logData = Arrays.copyOf(logData, logData.length + 1);
            logData[logData.length - 1] = this;
        }
        LOG.info(ajaxMsg.getMessage() + logMsg + logString, logData);
        throw this;
    }

    /**
     * Throws this exception and log with the params and this exception
     *
     * @param logString
     * @param logData
     */
    public void raiseThis(String logString, Object... logData) {
        LOG.info(ajaxMsg.getMessage() + logMsg + logString, logData, this);
        throw this;
    }

    public AjaxMsg getAjaxMsg() {
        return ajaxMsg;
    }

    public void setAjaxMsg(AjaxMsg ajaxMsg) {
        this.ajaxMsg = ajaxMsg;
    }

    @Override
    public String getMessage() {
        if (this.ajaxMsg != null) {
            return super.getMessage() + " -> " + this.getClass().getSimpleName() + ": " + ajaxMsg.toString();
        }
        return super.getMessage();
    }


    public static <T> T rethrow(Supplier<T> supplier, String msg) {
        try {
            return supplier.get();
        } catch (BusiLogicException e) {
            AjaxMsg ajaxMsg = e.getAjaxMsg();
            if (ajaxMsg == null) {
                throw BusiLogicException.create(e).init(msg + e.getMessage());
            }
            ajaxMsg.setMessage(msg + ajaxMsg.getMessage());
            throw e;
        } catch (Exception e) {
            LOG.warn("rethrow untouched for {}", msg, e);
            throw e;
        }
    }

    public static void rethrow(Runnable runnable, String msg) {
        try {
            runnable.run();
        } catch (BusiLogicException e) {
            AjaxMsg ajaxMsg = e.getAjaxMsg();
            if (ajaxMsg == null) {
                throw BusiLogicException.create(e).init(msg + e.getMessage());
            }
            ajaxMsg.setMessage(msg + ajaxMsg.getMessage());
            throw e;
        } catch (Exception e) {
            LOG.warn("rethrow untouched for {}", msg, e);
            throw e;
        }
    }

    public static void checkNotNull(String msg, Object... objs) {
        for (Object obj : objs) {
            if (Objects.isNull(obj)) {
                throw BusiLogicException.create(new NullPointerException("null")).init(msg);
            }
        }

    }
}
