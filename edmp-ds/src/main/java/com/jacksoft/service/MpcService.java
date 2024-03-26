package com.jacksoft.service;

import com.alibaba.fastjson.JSON;
import com.jacksoft.core.PlanManager;
import com.jacksoft.core.runnable.ProductRunnable;
import com.jacksoft.dao.LineinfoDao;
import com.jacksoft.dao.MpcDao;
import com.jacksoft.dao.TaskDao;
import com.jacksoft.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生产计划信息服务类
 */
@Slf4j
@Service
public class MpcService {

    @Resource
    private MpcDao mpcDao;

    @Resource
    private TaskDao taskDao;

    @Resource
    private LineinfoDao lineinfoDao;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private PlanManager planManager;

    /**
     * 分页查询
     * @param parameters
     * @return
     */
    public PagetationRes queryMpcByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row     =   10;
            int page    =   1;
            String id   =   null;
            String department  = null;
            String product =  null;
            String classes =  null;
            String line =  null;
            String mfg =  null;
            String batch =  null;
            String timezone =  null;

            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id  =   null!=filters.get("id")?filters.get("id").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                product = null!=filters.get("product")?filters.get("product").toString():null;
                classes = null!=filters.get("classes")?filters.get("classes").toString():null;
                line = null!=filters.get("line")?filters.get("line").toString():null;
                mfg = null!=filters.get("mfg")?filters.get("mfg").toString():null;
                batch = null!=filters.get("batch")?filters.get("batch").toString():null;
                timezone = null!=filters.get("timezone")?filters.get("timezone").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  mpcDao.countMpc(id,department,product,classes,timezone,line,mfg,batch);
            if(result.total>0){
                result.rows =  mpcDao.queryMpcByPage(id,department,product,classes,timezone,line,mfg,batch,row,page);
            }else{
                result.rows =   new ArrayList<>();
            }
            return result;
        }else{
            return null;
        }
    }

    /**
     * 新增
     * @param parameters
     * @return
     */
    public boolean insert(Object parameters) throws Exception{
        boolean result  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){
                List<Manufacture> list = (List<Manufacture>)paraMap.get("content");
                log.info("将要写入的生产计划信息：{}",list);
                int resInt  =   mpcDao.insMpc(list);
                if(resInt>0){
                    result = true;
                }else if(resInt==0) {
                    result = true;
                }else{
                    result =false;
                }
            }
        }else{
            log.info("新增生产计划信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 更新
     * @param parameter
     * @return
     * @throws Exception
     */
    public boolean update(Object parameter) throws Exception{
        boolean result  = false;
        if(null!=parameter){
            Map<String,Object> paraMap     =   JSON.parseObject(parameter.toString(),Map.class);
            if(null!=paraMap.get("filter") && null!=paraMap.get("replacement")){
                Manufacture filter         =   JSON.parseObject(paraMap.get("filter").toString(),Manufacture.class);
                Manufacture replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Manufacture.class);
                if(mpcDao.updMpc(filter,replacement)>0){
                    result  =   true;
                    Product product     =   new Product();
                    List<Product> list  =   new ArrayList<>();
                    product.batch       =   replacement.getBatch()!=null ? replacement.getBatch() : filter.getBatch();
                    product.id          =   replacement.getProduct()!=null ? replacement.getProduct() : filter.getProduct();
                    list.add(product);
                    ProductRunnable runnable = new ProductRunnable(applicationContext,list);
                    Thread thread = new Thread(runnable);
                    thread.run();
                }else{
                    log.info("更新生产计划信息操作失败!");
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
            }
        }else{
            log.info("更新生产计划信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 删除
     * @param parameter
     * @return
     * @throws Exception
     */
    public boolean delete(Object parameter) throws Exception{
        boolean result  = false;
        if(null!=parameter){
            Map<String,Object> paraMap  =   JSON.parseObject(parameter.toString(),Map.class);
            if(null!=paraMap.get("filter")){
                if(mpcDao.delMpc((List<Manufacture>)paraMap.get("filter"))>=0){
                    result  =   true;
                    List<Product> list  =   new ArrayList<>();

                    for(Map manufacture : (List<Map>)paraMap.get("filter"))
                    {
                        Product product     =   new Product();
                        product.batch       =   manufacture.get("batch")!=null ? manufacture.get("batch").toString() : null;
                        product.id          =   manufacture.get("product")!=null ? manufacture.get("product").toString() : null;
                        list.add(product);
                    }
                    ProductRunnable runnable = new ProductRunnable(applicationContext,list);
                    Thread thread = new Thread(runnable);
                    thread.run();
                }else{
                    log.info("SQL语句执行异常，无法删除生产计划信息");
                }
            }else{
                log.info("没有规范使用参数，缺少filter");
            }
        }else{
            log.info("删除生产计划信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 查询指定生产线的当日生产计划信息
     * @param parameters
     * @return
     */
    public Map<String,Object> quyCapaByLine(Object parameters) throws Exception {
        Map<String,Object> result =    null;
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            String department  = null;
            String classes =  null;
            String line =  null;
            String mfg =  null;

            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                department = null!=filters.get("department")?filters.get("department").toString():null;
                classes = null!=filters.get("classes")?filters.get("classes").toString():null;
                line = null!=filters.get("line")?filters.get("line").toString():null;
                mfg = null!=filters.get("mfg")?filters.get("mfg").toString():null;
            }
            List<Task> tasks = taskDao.quyTaskOnly(classes,null,department);
            if(null!=tasks && !tasks.isEmpty()) {
                result =  new HashMap<>();
                String[] taskArray      = new String[tasks.size()];
                int[]    capacityArray  = new int[tasks.size()];
                int[]    planArray      = new int[tasks.size()];
                for(Task task : tasks){
                    taskArray[task.getSerial()-1] = task.getWtz();
                }

                List<Manufacture> manufactures = mpcDao.quyCapaByLine(line, department,classes,mfg);
                if(null!=manufactures) {
                    for (Manufacture manufacture : manufactures){
                        capacityArray[Integer.parseInt(manufacture.getTimezone())-1] = manufacture.getOutput();
                        planArray[Integer.parseInt(manufacture.getTimezone())-1] = manufacture.getPlan();
                    }
                }else{
                    log.info("没有定义指定的班制的生产计划");
                }
                result.put("xAxis",taskArray);
                result.put("capacity",capacityArray);
                result.put("plan",planArray);
            }else{
                log.info("没有定义指定班制的执行时段,班制:{},部门:{}",classes,department);
            }
        }else{
            log.info("缺少必要的查询参数，无法查询指定生产线的当日生产计划信息");
        }
        return result;
    }

    /**
     * 查询指定生产线的当月产量计划信息
     * @param parameters
     * @return
     */
    public Map<String,Object> quyProduction(Object parameters) throws Exception {
        Map<String,Object> result =    null;
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            String department  = null;
            String classes =  null;
            String line =  null;
            String mfg =  null;

            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                department = null!=filters.get("department")?filters.get("department").toString():null;
                classes = null!=filters.get("classes")?filters.get("classes").toString():null;
                line = null!=filters.get("line")?filters.get("line").toString():null;
                mfg = null!=filters.get("mfg")?filters.get("mfg").toString():null;
            }
            List<Manufacture> data = mpcDao.quyProduction(line,department,classes,mfg);
            if(null!=data && !data.isEmpty()){
                result  =   new HashMap<>();
                List<Manufacture> filted = new ArrayList<>();
                for(int i=0;i<data.size();i++){
                    if(!"-".equals(data.get(i).getProduct())) {
                        filted.add(data.get(i));
                    }
                }
                String[] xAxis      =   new String[filted.size()];
                int[]    finish     =   new int[filted.size()];
                int[]    residue    =   new int[filted.size()];
                for(int i=0;i<filted.size();i++){
                    xAxis[i] = filted.get(i).getProduct() + "-" + filted.get(i).getBatch();
                    finish[i] = filted.get(i).getOutput();
                    residue[i] = filted.get(i).getPlan() - filted.get(i).getOutput();
                }
                result.put("xAxis",xAxis);
                result.put("finish",finish);
                result.put("residue",residue);
            }else{
                log.info("没有查询到产量信息，查询条件:{}",filter);
            }
        }else{
            log.info("缺少必要的查询参数，无法查询指定生产线的当日生产计划信息");
        }
        return result;
    }

    /**
     * 查询指定的产量计划信息
     * @param parameters
     * @return
     */
    public Map<String,Object> quyAllProduction(Object parameters) throws Exception {
        Map<String,Object> result =    null;
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            String department  = null;
            String classes =  null;
            String line =  null;
            String mfg =  null;

            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                department = null!=filters.get("department")?filters.get("department").toString():null;
                classes = null!=filters.get("classes")?filters.get("classes").toString():null;
                line = null!=filters.get("line")?filters.get("line").toString():null;
                mfg = null!=filters.get("mfg")?filters.get("mfg").toString():null;
            }
            List<Task> tasks       = taskDao.quyTaskOnly(classes,null,null);
            List<Task> tasks1      = taskDao.quyTaskOnly("1",null,null);
            String[] xAxis         = new String[tasks.size()];
            List<Map> series       = new ArrayList<>();
            if(null!=tasks && !tasks.isEmpty()) {
                for (Task task : tasks) {
                     if("1".equals(classes)){
                         xAxis[task.getSerial() - 1] = task.getWtz();
                     }else if("2".equals(classes)){
                         xAxis[task.getSerial() - tasks1.size()-1] = task.getWtz();
                     }
                }
                List<Lineinfo> lines = lineinfoDao.quyAplOnly(null, null, null);
                if(null!=lines && !lines.isEmpty()) {
                    String[] legend =   new String[lines.size()];
                    int i   =   0;
                    for(Lineinfo lineinfo : lines) {
                        legend[i++] = lineinfo.id + "-" + lineinfo.department;
                        List<Manufacture> manufactures = mpcDao.quyCapaByLine(lineinfo.id,lineinfo.department,classes,mfg);
                        if(null!=manufactures && !manufactures.isEmpty()){
                            Map<String,Object> production = new HashMap<>();
                            production.put("name",lineinfo.id+"-"+lineinfo.department);
                            int[] data = new int[tasks.size()];
                            for(Manufacture manufacture : manufactures){
                                data[Integer.parseInt(manufacture.getTimezone())-1] = manufacture.getOutput();
                            }
                            production.put("data",data);
                            series.add(production);
                        }else{
                            Map<String,Object> production = new HashMap<>();
                            production.put("name",lineinfo.id+"-"+lineinfo.department);
                            int[] data = new int[tasks.size()];
                            production.put("data",data);
                            series.add(production);
                        }
                    }
                    result = new HashMap<>();
                    result.put("xAxis",xAxis);
                    result.put("series",series);
                    result.put("legend",legend);
                }else{
                    log.info("没有查询到计划信息，不能生成产量信息");
                }
            }else{
                log.info("没有查询到指定的班制信息，不能生成产量信息");
            }
        }else{
            log.info("缺少必要的查询参数，无法查询指定生产线的当日生产计划信息");
        }
        return result;
    }

    /**
     * 查询当月指定的产量计划信息
     * @param parameters
     * @return
     */
    public Map<String,Object> quyCapaOfDate(Object parameters) throws Exception {
        Map<String,Object> result =    null;
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            String mfg =  null;
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                mfg = null!=filters.get("mfg")?filters.get("mfg").toString():null;
            }
            mfg = null!=mfg? mfg.substring(0,6)+"%" : null;
            List<Manufacture> data = mpcDao.quyCapaOfDate(mfg);
            if(null!=data && !data.isEmpty()){
                result = new HashMap<>();
                String[] xAxis = new String[data.size()];
                int[] series   = new int[data.size()];
                int i = 0;
                for(Manufacture manufacture : data){
                    xAxis[i]  = manufacture.getMfg();
                    series[i] = manufacture.getOutput();
                    i++;
                }
                result.put("xAxis",xAxis);
                result.put("series",series);
            }else{
                log.info("没有查询到生产计划信息，无法生成本月总产量信息");
            }

        }else{
            log.info("缺少必要的查询参数，无法查询指定生产线的当日生产计划信息");
        }
        return result;
    }

    /**
     * 自动排计划
     * @param parameters
     * @return
     * @throws Exception
     */
    public boolean autoEditPlan(Object parameters) throws Exception{
        boolean result  =   false;
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            Product product             =   new Product();
            String bgdate               =   null;
            String eddate               =   null;
            String classes              =   null;
            String timezone             =   null;
            int    plan                 =   0;
            String[] lines              =   null;
            boolean bChange             =   false;

            if(null!=filter.get("product") && null!=filter.get("plan")){
                Map<String,Object> productMap  =   JSON.parseObject(filter.get("product").toString(),Map.class);
                Map<String,Object> planMap     =   JSON.parseObject(filter.get("plan").toString(),Map.class);
                product.id  =   null!=productMap && null!=productMap.get("id") ? productMap.get("id").toString() : null;
                product.batch  =   null!=productMap && null!=productMap.get("batch") ? productMap.get("batch").toString() : null;
                product.order  =   null!=productMap && null!=productMap.get("order") ? Integer.parseInt(productMap.get("order").toString()) : 0;
                product.actuality  =   null!=productMap && null!=productMap.get("actuality") ? Integer.parseInt(productMap.get("actuality").toString()) : 0;
                product.department  =   null!=productMap && null!=productMap.get("department") ? productMap.get("department").toString() : null;
                product.capacity  =   null!=productMap && null!=productMap.get("capacity") ? Integer.parseInt(productMap.get("capacity").toString()) : 0;
                product.finish  =   null!=productMap && null!=productMap.get("finish") ? Integer.parseInt(productMap.get("finish").toString()) : 0;
                product.today  =   null!=productMap && null!=productMap.get("today") ? productMap.get("today").toString() : null;
                product.state  =   null!=productMap && null!=productMap.get("state") ? productMap.get("state").toString() : null;
                bgdate = null!=planMap && null!=planMap.get("bgdate") ? planMap.get("bgdate").toString() : null;
                eddate = null!=planMap && null!=planMap.get("eddate") ? planMap.get("eddate").toString() : null;
                classes = null!=planMap && null!=planMap.get("classes") ? planMap.get("classes").toString() : null;
                timezone = null!=planMap && null!=planMap.get("timezone") ? planMap.get("timezone").toString() : null;
                plan = null!=planMap && null!=planMap.get("planCapa") ? Integer.parseInt(planMap.get("planCapa").toString()) : 0;
                String change = null!=planMap && null!=planMap.get("change") ? planMap.get("change").toString() : null;
                List<String> list = null!=planMap && null!=planMap.get("lines") ? JSON.parseObject(planMap.get("lines").toString(),List.class) : null;
                if(null!=list){
                    lines = new String[list.size()];
                    for(int i=0;i<list.size();i++){
                        lines[i] = list.get(i).toString();
                    }
                }
                if("1".equals(change)){
                    bChange = false;
                }else{
                    bChange = true;
                }

            }

            result = planManager.toEditPlan(product,bgdate,classes,plan,lines,bChange,eddate,timezone);

        }else{
            log.info("缺少必要的查询参数，无法查询指定生产线的当日生产计划信息");
        }
        return result;
    }

    /**
     * 生成全天生产计划（指定日期、生产线、部门），日期是生产日期：即当天的白班+次日的夜班
     * @return
     */
    public List<Map> getAlldayPlan(Object parameters){
        List<Map> result = null;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            Map<String,Object> filters  = (Map<String,Object>)paraMap.get("filters");
            String department           = filters.get("department").toString();
            String mfg                  = filters.get("mfg").toString();
            String line                 = filters.get("line").toString();

            result =    planManager.getAlldayPlan(line,department,mfg);

        }else{
            log.info("缺少必要的查询参数，无法查询指定生产线的指定日期的全天生产计划信息");
        }
        return result;
    }

}
