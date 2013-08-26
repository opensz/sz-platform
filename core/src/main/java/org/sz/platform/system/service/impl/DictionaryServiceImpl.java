package org.sz.platform.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.platform.system.dao.DictionaryDao;
import org.sz.platform.system.dao.GlobalTypeDao;
import org.sz.platform.system.model.Dictionary;
import org.sz.platform.system.model.GlobalType;
import org.sz.platform.system.service.DictionaryService;

@Service("dictionaryService")
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary>
		implements DictionaryService {

	@Resource
	private DictionaryDao dictionaryDao;

	@Resource
	private GlobalTypeDao globalTypeDao;

	protected IEntityDao<Dictionary, Long> getEntityDao() {
		return this.dictionaryDao;
	}

	public List<Dictionary> getByNodeKey(String nodeKey) {
		GlobalType globalType = this.globalTypeDao.getByDictNodeKey(nodeKey);
		long typeId = globalType.getTypeId().longValue();
		return this.dictionaryDao.getByTypeId(typeId);
	}

	public List<Dictionary> getByTypeId(long typeId, boolean needRoot) {
		GlobalType globalType = (GlobalType) this.globalTypeDao.getById(Long
				.valueOf(typeId));

		List<Dictionary> list = this.dictionaryDao.getByTypeId(typeId);
		for (Dictionary dic : list) {
			dic.setType(globalType.getType());
		}
		if (needRoot) {
			Dictionary dictionary = new Dictionary();
			dictionary.setDicId(Long.valueOf(typeId));
			dictionary.setParentId(Long.valueOf(0L));
			dictionary.setItemName(globalType.getTypeName());
			dictionary.setType(globalType.getType());
			list.add(0, dictionary);
		}
		return list;
	}

	public void delByDicId(Long dicId) {
		Dictionary dictionary = (Dictionary) this.dictionaryDao.getById(dicId);
		String nodePath = dictionary.getNodePath();
		List<Dictionary> list = this.dictionaryDao.getByNodePath(nodePath);
		for (Dictionary dic : list)
			this.dictionaryDao.delById(dic.getDicId());
	}

	public boolean isItemKeyExists(long typeId, String itemKey) {
		return this.dictionaryDao.isItemKeyExists(typeId, itemKey);
	}

	public boolean isItemKeyExistsForUpdate(long dicId, long typeId,
			String itemKey) {
		return this.dictionaryDao.isItemKeyExistsForUpdate(dicId, typeId,
				itemKey);
	}

	public void updSn(Long[] lAryId) {
		if (BeanUtils.isEmpty(lAryId))
			return;
		for (int i = 0; i < lAryId.length; i++) {
			int sn = i + 1;
			Long dicId = lAryId[i];
			this.dictionaryDao.updSn(dicId, Integer.valueOf(sn));
		}
	}

	public void move(Long targetId, Long dragId, String moveType) {
		Dictionary target = (Dictionary) this.dictionaryDao.getById(targetId);
		Dictionary dragged = (Dictionary) this.dictionaryDao.getById(dragId);
		String nodePath;
		if (("prev".equals(moveType)) || ("next".equals(moveType))) {
			String targetNodePath = target.getNodePath();
			int idx = targetNodePath.lastIndexOf(target.getDicId() + ".");
			String basePath = targetNodePath.substring(0, idx);
			String dragedNodePath = basePath + dragId + ".";
			dragged.setNodePath(dragedNodePath);
			dragged.setParentId(target.getParentId());
			if ("prev".equals(moveType)) {
				dragged.setSn(Long.valueOf(target.getSn().longValue() - 1L));
			} else {
				dragged.setSn(Long.valueOf(target.getSn().longValue() + 1L));
			}
			this.dictionaryDao.update(dragged);
		} else {
			nodePath = dragged.getNodePath();
			List<Dictionary> list = this.dictionaryDao.getByNodePath(nodePath);

			for (Dictionary dictionary : list) {
				if (dictionary.getDicId().equals(dragId)) {
					dictionary.setParentId(targetId);

					dictionary.setNodePath(target.getNodePath()
							+ dictionary.getDicId() + ".");
				} else {
					String path = dictionary.getNodePath();

					String tmpPath = path.replaceAll(nodePath, "");

					String targetPath = target.getNodePath();

					String tmp = targetPath + dragged.getDicId() + "."
							+ tmpPath;

					dictionary.setNodePath(tmp);
				}
				this.dictionaryDao.update(dictionary);
			}
		}
	}
}
