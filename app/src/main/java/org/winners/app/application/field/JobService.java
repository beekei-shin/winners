package org.winners.app.application.field;

import org.springframework.stereotype.Service;
import org.winners.app.application.field.dto.GetJobListSearchFieldDTO;
import org.winners.app.application.field.dto.JobDTO;

import java.util.List;

@Service
public interface JobService {

    List<JobDTO> getJobList(GetJobListSearchFieldDTO searchField);

}
