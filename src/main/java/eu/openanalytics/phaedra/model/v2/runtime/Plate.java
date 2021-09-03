package eu.openanalytics.phaedra.model.v2.runtime;

import eu.openanalytics.phaedra.model.v2.enumeration.ApprovalStatus;
import eu.openanalytics.phaedra.model.v2.enumeration.CalculationStatus;
import eu.openanalytics.phaedra.model.v2.enumeration.LinkStatus;
import eu.openanalytics.phaedra.model.v2.enumeration.UploadStatus;
import eu.openanalytics.phaedra.model.v2.enumeration.ValidationStatus;
import lombok.Value;

import java.util.Date;

@Value
public class Plate {
	Long id;

	String barcode;
	String description;

	Long experimentId;

	Integer rows;
	Integer columns;
	Integer sequence;


	LinkStatus linkStatus;
	String linkSource;
	String linkTemplateId;
	Date linkedOn;

	CalculationStatus calculationStatus;
	String calculationError;
	String calculatedBy;
	Date calculatedOn;

	ValidationStatus validationStatus;
	String validatedBy;
	Date validatedOn;

	ApprovalStatus approvalStatus;
	String approvedBy;
	Date approvedOn;

	UploadStatus uploadStatus;
	String uploadedBy;
	Date uploadedOn;

	Date createdOn;
	String createdBy;
	Date updatedOn;
	String updatedBy;

}
