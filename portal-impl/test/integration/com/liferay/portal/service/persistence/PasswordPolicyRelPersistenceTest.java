/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchPasswordPolicyRelException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.PasswordPolicyRel;
import com.liferay.portal.model.impl.PasswordPolicyRelModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PropsValues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PasswordPolicyRelPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (PasswordPolicyRelPersistence)PortalBeanLocatorUtil.locate(PasswordPolicyRelPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PasswordPolicyRel passwordPolicyRel = _persistence.create(pk);

		Assert.assertNotNull(passwordPolicyRel);

		Assert.assertEquals(passwordPolicyRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		_persistence.remove(newPasswordPolicyRel);

		PasswordPolicyRel existingPasswordPolicyRel = _persistence.fetchByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		Assert.assertNull(existingPasswordPolicyRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPasswordPolicyRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PasswordPolicyRel newPasswordPolicyRel = _persistence.create(pk);

		newPasswordPolicyRel.setPasswordPolicyId(ServiceTestUtil.nextLong());

		newPasswordPolicyRel.setClassNameId(ServiceTestUtil.nextLong());

		newPasswordPolicyRel.setClassPK(ServiceTestUtil.nextLong());

		_persistence.update(newPasswordPolicyRel, false);

		PasswordPolicyRel existingPasswordPolicyRel = _persistence.findByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		Assert.assertEquals(existingPasswordPolicyRel.getPasswordPolicyRelId(),
			newPasswordPolicyRel.getPasswordPolicyRelId());
		Assert.assertEquals(existingPasswordPolicyRel.getPasswordPolicyId(),
			newPasswordPolicyRel.getPasswordPolicyId());
		Assert.assertEquals(existingPasswordPolicyRel.getClassNameId(),
			newPasswordPolicyRel.getClassNameId());
		Assert.assertEquals(existingPasswordPolicyRel.getClassPK(),
			newPasswordPolicyRel.getClassPK());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		PasswordPolicyRel existingPasswordPolicyRel = _persistence.findByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		Assert.assertEquals(existingPasswordPolicyRel, newPasswordPolicyRel);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchPasswordPolicyRelException");
		}
		catch (NoSuchPasswordPolicyRelException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		PasswordPolicyRel existingPasswordPolicyRel = _persistence.fetchByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		Assert.assertEquals(existingPasswordPolicyRel, newPasswordPolicyRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PasswordPolicyRel missingPasswordPolicyRel = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPasswordPolicyRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicyRel.class,
				PasswordPolicyRel.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("passwordPolicyRelId",
				newPasswordPolicyRel.getPasswordPolicyRelId()));

		List<PasswordPolicyRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PasswordPolicyRel existingPasswordPolicyRel = result.get(0);

		Assert.assertEquals(existingPasswordPolicyRel, newPasswordPolicyRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicyRel.class,
				PasswordPolicyRel.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("passwordPolicyRelId",
				ServiceTestUtil.nextLong()));

		List<PasswordPolicyRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicyRel.class,
				PasswordPolicyRel.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"passwordPolicyRelId"));

		Object newPasswordPolicyRelId = newPasswordPolicyRel.getPasswordPolicyRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("passwordPolicyRelId",
				new Object[] { newPasswordPolicyRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPasswordPolicyRelId = result.get(0);

		Assert.assertEquals(existingPasswordPolicyRelId, newPasswordPolicyRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicyRel.class,
				PasswordPolicyRel.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"passwordPolicyRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("passwordPolicyRelId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		_persistence.clearCache();

		PasswordPolicyRelModelImpl existingPasswordPolicyRelModelImpl = (PasswordPolicyRelModelImpl)_persistence.findByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		Assert.assertEquals(existingPasswordPolicyRelModelImpl.getClassNameId(),
			existingPasswordPolicyRelModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingPasswordPolicyRelModelImpl.getClassPK(),
			existingPasswordPolicyRelModelImpl.getOriginalClassPK());

		Assert.assertEquals(existingPasswordPolicyRelModelImpl.getPasswordPolicyId(),
			existingPasswordPolicyRelModelImpl.getOriginalPasswordPolicyId());
		Assert.assertEquals(existingPasswordPolicyRelModelImpl.getClassNameId(),
			existingPasswordPolicyRelModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingPasswordPolicyRelModelImpl.getClassPK(),
			existingPasswordPolicyRelModelImpl.getOriginalClassPK());
	}

	protected PasswordPolicyRel addPasswordPolicyRel()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PasswordPolicyRel passwordPolicyRel = _persistence.create(pk);

		passwordPolicyRel.setPasswordPolicyId(ServiceTestUtil.nextLong());

		passwordPolicyRel.setClassNameId(ServiceTestUtil.nextLong());

		passwordPolicyRel.setClassPK(ServiceTestUtil.nextLong());

		_persistence.update(passwordPolicyRel, false);

		return passwordPolicyRel;
	}

	private PasswordPolicyRelPersistence _persistence;
}