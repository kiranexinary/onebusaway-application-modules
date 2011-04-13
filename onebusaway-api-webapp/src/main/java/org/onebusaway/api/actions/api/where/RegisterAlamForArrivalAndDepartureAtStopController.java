package org.onebusaway.api.actions.api.where;

import java.util.Date;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.onebusaway.api.actions.api.ApiActionSupport;
import org.onebusaway.api.model.transit.BeanFactoryV2;
import org.onebusaway.exceptions.ServiceException;
import org.onebusaway.transit_data.model.ArrivalAndDepartureBean;
import org.onebusaway.transit_data.model.ArrivalAndDepartureForStopQueryBean;
import org.onebusaway.transit_data.services.TransitDataService;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

public class RegisterAlamForArrivalAndDepartureAtStopController extends
    ApiActionSupport {

  private static final long serialVersionUID = 1L;

  private static final int V2 = 2;

  @Autowired
  private TransitDataService _service;

  private ArrivalAndDepartureForStopQueryBean _query = new ArrivalAndDepartureForStopQueryBean();

  public RegisterAlamForArrivalAndDepartureAtStopController() {
    super(V2);
  }

  @RequiredFieldValidator(message = Messages.MISSING_REQUIRED_FIELD)
  public void setId(String id) {
    _query.setStopId(id);
  }

  public String getId() {
    return _query.getStopId();
  }

  @RequiredFieldValidator(message = Messages.MISSING_REQUIRED_FIELD)
  public void setTripId(String tripId) {
    _query.setTripId(tripId);
  }

  public String getTripId() {
    return _query.getTripId();
  }

  @RequiredFieldValidator(message = Messages.MISSING_REQUIRED_FIELD)
  @TypeConversion(converter = "org.onebusaway.presentation.impl.conversion.DateConverter")
  public void setServiceDate(Date date) {
    _query.setServiceDate(date.getTime());
  }

  public Date getServiceDate() {
    if (_query.getServiceDate() == 0)
      return null;
    return new Date(_query.getServiceDate());
  }

  public void setVehicleId(String vehicleId) {
    _query.setVehicleId(vehicleId);
  }

  public String getVehicleId() {
    return _query.getVehicleId();
  }

  public void setStopSequence(int stopSequence) {
    _query.setStopSequence(stopSequence);
  }

  public int getStopSequence() {
    return _query.getStopSequence();
  }

  public void setOnArrival(boolean onArrival) {

  }

  public void setAlarmTimeOffset(int alarmTimeOffset) {

  }

  public void setAlarmMethod(String alarmMethod) {

  }

  public void setAlarmData(String alarmData) {

  }

  public DefaultHttpHeaders show() throws ServiceException {

    if (hasErrors())
      return setValidationErrorsResponse();

    if (_query.getTime() == 0)
      _query.setTime(System.currentTimeMillis());

    ArrivalAndDepartureBean result = _service.getArrivalAndDepartureForStop(_query);

    if (result == null)
      return setResourceNotFoundResponse();

    if (isVersion(V2)) {
      BeanFactoryV2 factory = getBeanFactoryV2();
      return setOkResponse(factory.getResponse(result));
    } else {
      return setUnknownVersionResponse();
    }
  }
}
