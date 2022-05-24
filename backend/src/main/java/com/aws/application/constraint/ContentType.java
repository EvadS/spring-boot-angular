package com.aws.application.constraint;


import com.aws.application.constraint.impl.ContentTypeMultipartFileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


/**
 * The annotated element must have specified content type.
 *
 * Supported types are:
 * <ul>
 * <li><code>MultipartFile</code></li>
 * </ul>
 *
 */

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ContentTypeMultipartFileValidator.class})
public @interface ContentType {

    String message() default  "Invalid image file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Specify accepted content types.
     *
     * Content type example :
     * <ul>
     * <li>application/pdf - accepts PDF documents only</li>
     * <li>application/msword - accepts MS Word documents only</li>
     * <li>images/png - accepts PNG images only</li>
     * </ul>
     *
     * @return accepted content types
     */
    String[] value();
}