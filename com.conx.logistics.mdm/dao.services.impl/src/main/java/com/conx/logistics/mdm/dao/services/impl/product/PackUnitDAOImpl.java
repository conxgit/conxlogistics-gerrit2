package com.conx.logistics.mdm.dao.services.impl.product;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.mdm.dao.services.product.IPackUnitDAOService;
import com.conx.logistics.mdm.domain.constants.PackUnitCustomCONSTANTS;
import com.conx.logistics.mdm.domain.product.PackUnit;

@Transactional
@Repository
public class PackUnitDAOImpl implements IPackUnitDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext(unitName="pu")
    private EntityManager em;	
    
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public PackUnit get(long id) {
		return em.getReference(PackUnit.class, id);
	}    

	@Override
	public List<PackUnit> getAll() {
		return em.createQuery("select o from com.conx.logistics.mdm.domain.product.PackUnit o record by o.id",PackUnit.class).getResultList();
	}
	
	@Override
	public PackUnit getByCode(String code) {
		PackUnit org = null;
		
		try
		{
			TypedQuery<PackUnit> q = em.createQuery("select o from com.conx.logistics.mdm.domain.product.PackUnit o WHERE o.code = :code",PackUnit.class);
			q.setParameter("code", code);
						
			org = q.getSingleResult();
		}
		catch(NoResultException e){}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		catch(Error e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}		
		
		return org;
	}	

	@Override
	public PackUnit add(PackUnit record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(PackUnit record) {
		em.remove(record);
	}

	@Override
	public PackUnit update(PackUnit record) {
		return em.merge(record);
	}


	@Override
	public PackUnit provide(PackUnit record) {
		PackUnit existingRecord = getByCode(record.getCode());
		if (Validator.isNull(existingRecord))
		{		
			record = update(record);
			try {
				//em.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return record;
	}
	
	@Override
	public PackUnit provide(String code, String name) {
		PackUnit res = null;
		if ((res = getByCode(code)) == null)
		{
			PackUnit unit = new PackUnit();

			unit.setCode(code);
			unit.setName(name);

			res = add(unit);
		}
		return res;
	}	
	
	@Override
	public void provideDefaults()
	{
		provide(PackUnitCustomCONSTANTS.TYPE_BAG, PackUnitCustomCONSTANTS.TYPE_BAG_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_BBG, PackUnitCustomCONSTANTS.TYPE_BBG_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_BBK, PackUnitCustomCONSTANTS.TYPE_BBK_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_BLC, PackUnitCustomCONSTANTS.TYPE_BLC_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_BLU, PackUnitCustomCONSTANTS.TYPE_BLU_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_BND, PackUnitCustomCONSTANTS.TYPE_BND_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_BOX, PackUnitCustomCONSTANTS.TYPE_BOX_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_BSK, PackUnitCustomCONSTANTS.TYPE_BSK_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_CAS, PackUnitCustomCONSTANTS.TYPE_CAS_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_CTN, PackUnitCustomCONSTANTS.TYPE_CTN_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_CYL, PackUnitCustomCONSTANTS.TYPE_CYL_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_DOZ, PackUnitCustomCONSTANTS.TYPE_DOZ_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_DRM, PackUnitCustomCONSTANTS.TYPE_DRM_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_ENV, PackUnitCustomCONSTANTS.TYPE_ENV_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_GRS, PackUnitCustomCONSTANTS.TYPE_GRS_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_KEG, PackUnitCustomCONSTANTS.TYPE_KEG_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_MIX, PackUnitCustomCONSTANTS.TYPE_MIX_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_PAI, PackUnitCustomCONSTANTS.TYPE_PAI_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_PCE, PackUnitCustomCONSTANTS.TYPE_PCE_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_PKG, PackUnitCustomCONSTANTS.TYPE_PKG_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_PLT, PackUnitCustomCONSTANTS.TYPE_PLT_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_REL, PackUnitCustomCONSTANTS.TYPE_REL_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_RLL, PackUnitCustomCONSTANTS.TYPE_RLL_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_SHT, PackUnitCustomCONSTANTS.TYPE_SHT_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_SKD, PackUnitCustomCONSTANTS.TYPE_SKD_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_SPL, PackUnitCustomCONSTANTS.TYPE_SPL_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_TUB, PackUnitCustomCONSTANTS.TYPE_TUB_DESCRIPTION);
		provide(PackUnitCustomCONSTANTS.TYPE_UNT, PackUnitCustomCONSTANTS.TYPE_UNT_DESCRIPTION);
	}
}
