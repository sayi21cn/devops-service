package io.choerodon.devops.app.service.impl;

import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.core.convertor.ConvertPageHelper;
import io.choerodon.core.domain.Page;
import io.choerodon.devops.api.dto.DevopsAutoDeployDTO;
import io.choerodon.devops.api.dto.DevopsAutoDeployRecordDTO;
import io.choerodon.devops.app.service.DevopsAutoDeployService;
import io.choerodon.devops.domain.application.entity.DevopsAutoDeployE;
import io.choerodon.devops.domain.application.entity.DevopsAutoDeployValueE;
import io.choerodon.devops.domain.application.repository.DevopsAutoDeployRecordRepository;
import io.choerodon.devops.domain.application.repository.DevopsAutoDeployRepository;
import io.choerodon.devops.domain.application.repository.DevopsAutoDeployValueRepository;
import io.choerodon.devops.domain.application.repository.IamRepository;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creator: ChangpingShi0213@gmail.com
 * Date:  16:33 2019/2/25
 * Description:
 */
@Service
public class DevopsAutoDeployServiceImpl implements DevopsAutoDeployService {
    @Autowired
    private IamRepository iamRepository;
    @Autowired
    private DevopsAutoDeployRepository devopsAutoDeployRepository;
    @Autowired
    private DevopsAutoDeployRecordRepository devopsAutoDeployRecordRepository;
    @Autowired
    private DevopsAutoDeployValueRepository devopsAutoDeployValueRepository;

    @Override
    public DevopsAutoDeployDTO createOrUpdate(Long projectId, DevopsAutoDeployDTO devopsAutoDeployDTO) {
        //校验project ID
//        iamRepository.queryIamProject(projectId);
        //校验taskName
        devopsAutoDeployRepository.checkTaskName(projectId, devopsAutoDeployDTO.getTaskName());
        DevopsAutoDeployE devopsAutoDeployE = ConvertHelper.convert(devopsAutoDeployDTO, DevopsAutoDeployE.class);
        devopsAutoDeployE.setProjectId(projectId);
        //保存value信息
        DevopsAutoDeployValueE devopsAutoDeployValueE = new DevopsAutoDeployValueE(devopsAutoDeployE.getValue());
        if (devopsAutoDeployE.getId() != null && devopsAutoDeployE.getValueId() != null) {
            devopsAutoDeployValueE.setId(devopsAutoDeployE.getValueId());
        }
        devopsAutoDeployE.setValueId(devopsAutoDeployValueRepository.createOrUpdate(devopsAutoDeployValueE));
        //保存autoDeploy信息
        return ConvertHelper.convert(devopsAutoDeployRepository.createOrUpdate(devopsAutoDeployE), DevopsAutoDeployDTO.class);
    }

    @Override
    public void delete(Long projectId, Long autoDeployId) {
//        iamRepository.queryIamProject(projectId);
        devopsAutoDeployRepository.delete(autoDeployId);
    }

    @Override
    public Page<DevopsAutoDeployDTO> listByOptions(Long projectId, Long appId, Long envId, Boolean doPage, PageRequest pageRequest, String params) {
//        iamRepository.queryIamProject(projectId);
        return ConvertPageHelper.convertPage(devopsAutoDeployRepository.listByOptions(projectId, appId, envId, doPage, pageRequest, params), DevopsAutoDeployDTO.class);
    }

    @Override
    public Page<DevopsAutoDeployRecordDTO> queryRecords(Long projectId, Long appId, Long envId, String taskName, Boolean doPage, PageRequest pageRequest, String params) {
//        iamRepository.queryIamProject(projectId);
        return ConvertPageHelper.convertPage(devopsAutoDeployRecordRepository.listByOptions(projectId, appId, envId, taskName, doPage, pageRequest, params), DevopsAutoDeployRecordDTO.class);
    }
}
