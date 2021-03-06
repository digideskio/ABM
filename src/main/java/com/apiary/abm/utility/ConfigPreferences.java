package com.apiary.abm.utility;


import com.apiary.abm.entity.BodyObjectEntity;
import com.apiary.abm.entity.TreeNodeEntity;
import com.apiary.abm.entity.config.ConfigClassInfoEntity;
import com.apiary.abm.entity.config.ConfigConfigurationEntity;
import com.apiary.abm.entity.config.ConfigEntityNameEntity;
import com.apiary.abm.entity.config.ConfigRootEntity;
import com.apiary.abm.ui.ABMToolWindow;
import com.intellij.openapi.project.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class ConfigPreferences
{
	private ConfigRootEntity mConfig;


	public ConfigPreferences()
	{
		try
		{
			if(!configExist())
			{
				mConfig = new ConfigRootEntity();
				saveConfig();
				if(!configExist())
				{
					mConfig = null;
					return;
				}
			}

			Project myProject = ABMToolWindow.getProject();
			File configFile = new File(myProject.getBaseDir().getPath() + "/abm_config.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(ConfigRootEntity.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			mConfig = (ConfigRootEntity) jaxbUnmarshaller.unmarshal(configFile);
		}
		catch(JAXBException e)
		{
			e.printStackTrace();
		}
	}


	private void saveConfig()
	{
		Project myProject = ABMToolWindow.getProject();
		File configFile = new File(myProject.getBaseDir().getPath() + "/abm_config.xml");

		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(ConfigRootEntity.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(mConfig, configFile);
			jaxbMarshaller.marshal(mConfig, System.out);
		}
		catch(JAXBException e)
		{
			e.printStackTrace();
		}
	}


	public static boolean configExist()
	{
		return new File(ABMToolWindow.getProject().getBaseDir().getPath() + "/abm_config.xml").exists();
	}


	public void saveConfigurationEntity(ConfigConfigurationEntity entity)
	{
		mConfig.setConfigurationEntity(entity);

		saveConfig();
	}


	public ConfigConfigurationEntity getConfigurationEntity()
	{
		if(mConfig.getConfigurationEntity() != null) return mConfig.getConfigurationEntity();
		else return new ConfigConfigurationEntity();
	}


	public void saveTreeNodeEntity(TreeNodeEntity entity, String oldUri, String oldMethod)
	{
		ConfigClassInfoEntity classInfoEntity = new ConfigClassInfoEntity();
		List<ConfigEntityNameEntity> requests = new ArrayList<ConfigEntityNameEntity>();
		List<ConfigEntityNameEntity> responses = new ArrayList<ConfigEntityNameEntity>();

		if(entity.getRequestBody() != null) for(BodyObjectEntity body : entity.getRequestBody())
			requests.add(new ConfigEntityNameEntity(body.getSerializableName(), body.getEntityName()));

		if(entity.getResponseBody() != null) for(BodyObjectEntity body : entity.getResponseBody())
			responses.add(new ConfigEntityNameEntity(body.getSerializableName(), body.getEntityName()));

		classInfoEntity.setUri(entity.getUri());
		classInfoEntity.setMethod(entity.getMethod());
		classInfoEntity.setMethodName(entity.getMethodName());
		classInfoEntity.setHidden(entity.getHidden());
		classInfoEntity.setAsync(entity.isAsync());
		classInfoEntity.setRequests(requests);
		classInfoEntity.setResponses(responses);

		if(!mConfig.setClassInfoItem(classInfoEntity, oldUri, oldMethod)) mConfig.addClassInfoItem(classInfoEntity);

		saveConfig();
	}


	public void saveTreeNodeEntity(TreeNodeEntity entity)
	{
		saveTreeNodeEntity(entity, null, null);
	}


	public void tryToFillTreeNodeEntity(TreeNodeEntity entity)
	{
		for(ConfigClassInfoEntity item : mConfig.getClassInfoList())
		{
			if(item.getMethod().equals(entity.getMethod()) && item.getUri().equals(entity.getUri()))
			{
				List<BodyObjectEntity> requests = entity.getRequestBody();
				List<BodyObjectEntity> responses = entity.getResponseBody();

				if(requests != null) for(ConfigEntityNameEntity bodyItem : item.getRequests())
					for(BodyObjectEntity bodyEntity : requests)
						if(bodyItem.getSerializableName() != null && bodyEntity.getSerializableName() != null && bodyItem.getSerializableName().equals(bodyEntity.getSerializableName()))
						{
							bodyEntity.setEntityName(bodyItem.getEntityName());
							break;
						}

				if(responses != null) for(ConfigEntityNameEntity bodyItem : item.getResponses())
					for(BodyObjectEntity bodyEntity : responses)
						if(bodyItem.getSerializableName().equals(bodyEntity.getSerializableName()))
						{
							bodyEntity.setEntityName(bodyItem.getEntityName());
							break;
						}

				entity.setMethodName(item.getMethodName());
				entity.setHidden(item.getHidden());
				entity.setAsync(item.isAsync());
				entity.setRequestBody(requests);
				entity.setResponseBody(responses);
			}
		}
	}


	public List<TreeNodeEntity> getAllConfigEntities()
	{
		List<TreeNodeEntity> list = new ArrayList<TreeNodeEntity>();

		for(ConfigClassInfoEntity item : mConfig.getClassInfoList())
		{
			TreeNodeEntity entity = new TreeNodeEntity();
			entity.setMethodName(item.getMethodName());
			entity.setMethod(item.getMethod());
			entity.setUri(item.getUri());
			entity.setHidden(item.getHidden());
			entity.setAsync(item.isAsync());

			if(item.getRequests() != null)
			{
				List<BodyObjectEntity> requestList = new ArrayList<BodyObjectEntity>();
				for(ConfigEntityNameEntity nameItem : item.getRequests())
				{
					BodyObjectEntity bodyEntity = new BodyObjectEntity(nameItem.getSerializableName(), nameItem.getEntityName(), null);
					requestList.add(bodyEntity);
				}
				entity.setRequestBody(requestList);
			}

			if(item.getResponses() != null)
			{
				List<BodyObjectEntity> responsesList = new ArrayList<BodyObjectEntity>();
				for(ConfigEntityNameEntity nameItem : item.getResponses())
				{
					BodyObjectEntity bodyEntity = new BodyObjectEntity(nameItem.getSerializableName(), nameItem.getEntityName(), null);
					responsesList.add(bodyEntity);
				}
				entity.setResponseBody(responsesList);
			}
			list.add(entity);
		}

		return list;
	}


	public static boolean removeConfigFile()
	{
		Project myProject = ABMToolWindow.getProject();
		File configFile = new File(myProject.getBaseDir().getPath() + "/abm_config.xml");
		return configFile.delete();
	}
}
