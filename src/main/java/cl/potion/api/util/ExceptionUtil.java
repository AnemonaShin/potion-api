package cl.potion.api.util;

import org.springframework.dao.DataIntegrityViolationException;

import cl.potion.api.exception.ExceptionList;
import cl.potion.api.exception.ServiceException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class ExceptionUtil {

    public final ExceptionList duplicateConstraintError(DataIntegrityViolationException daex)
            throws ServiceException {
        if (daex.getMessage().contains("(username)")) {
            log.error("USERNAME DUPLICATED");
            return ExceptionList.USUNAT;
        } else if (daex.getMessage().contains("(email)")) {
            log.error("EMAIL DUPLICATED");
            return ExceptionList.USEAT;
        } else {
            log.error("DEFAULT DUPLICATED");
            return ExceptionList.USDFDP;
        }
    }

}
