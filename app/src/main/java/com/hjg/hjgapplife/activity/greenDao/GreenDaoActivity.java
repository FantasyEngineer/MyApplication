package com.hjg.hjgapplife.activity.greenDao;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hjg.baseapp.activity.BaseActivity;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.greenDao.db.GreenDaoUtils;
import com.hjg.hjgapplife.activity.greenDao.entry.Person;
import com.hjg.hjgapplife.activity.greenDao.entry.PersonDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class GreenDaoActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    EditText etName;
    EditText etAge;
    Button btnAdd;
    Button btnUpdata;
    Button btnDelete;
    EditText etDeleteItem;
    Button btnQuery;
    Button btnQueryAsc;
    Button btnQueryNasc;
    TextView tvData;
    EditText etId;
    EditText etNameUpdata;
    EditText etAgeUpdata;
    Button btnDeleteId;
    EditText etDeleteItemId;
    Button btnRondom;
    Button btnSelsect;
    private PersonDao dao;
    private List<Person> personList;
    private Query<Person> personsQuery;
    private CheckBox cb;
    private Button btn_clearAll;

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "GreenDao的使用");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_green_dao;
    }

    @Override
    protected void initView() {
        etName = (EditText) findViewById(R.id.et_name);
        etAge = (EditText) findViewById(R.id.et_age);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnUpdata = (Button) findViewById(R.id.btn_updata);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        etDeleteItem = (EditText) findViewById(R.id.et_delete_item);
        btnQuery = (Button) findViewById(R.id.btn_query);
        btnQueryAsc = (Button) findViewById(R.id.btn_query_asc);
        btnQueryNasc = (Button) findViewById(R.id.btn_query_nasc);
        tvData = (TextView) findViewById(R.id.tv_data);
        etId = (EditText) findViewById(R.id.et_id);
        etNameUpdata = (EditText) findViewById(R.id.et_name_updata);
        etAgeUpdata = (EditText) findViewById(R.id.et_age_updata);
        btnDeleteId = (Button) findViewById(R.id.btn_delete_id);
        etDeleteItemId = (EditText) findViewById(R.id.et_delete_item_id);
        btnRondom = (Button) findViewById(R.id.btn_rondom);
        btnSelsect = (Button) findViewById(R.id.btn_selsect);
        btn_clearAll = (Button) findViewById(R.id.btn_clearAll);
        cb = (CheckBox) findViewById(R.id.cb);
    }

    @Override
    protected void initData() {
        dao = GreenDaoUtils.getInstance().getmDaoSession().getPersonDao();
        upDataView(dao.loadAll());

    }

    @Override
    public void initAction() {
        super.initAction();
        btnAdd.setOnClickListener(this);
        btnUpdata.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        btnQueryAsc.setOnClickListener(this);
        btnQueryNasc.setOnClickListener(this);
        btnDeleteId.setOnClickListener(this);
        btnSelsect.setOnClickListener(this);
        btnRondom.setOnClickListener(this);
        btn_clearAll.setOnClickListener(this);
        cb.setOnCheckedChangeListener(this);
    }

    /**
     * 更新数据库到界面展示
     */
    public void upDataView(List<Person> personList) {
        if (personList != null && !personList.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Person person : personList) {
                stringBuilder.append(person.toString() + "\n");
                Log.d("MainActivity", person.toString());
            }
            tvData.setText(stringBuilder.toString());
        } else {
            Toast.makeText(this, "未查询到相关数据", Toast.LENGTH_SHORT).show();
            tvData.setText("");
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                Person person = new Person();
                person.setName(etName.getText().toString());
                person.setAge(Integer.parseInt(etAge.getText().toString()));
                person.setIsBoy(false);
                Log.d("MainActivity", person.toString());
                dao.insert(person);
                //插入之后更新界面展示
                upDataView(dao.loadAll());
                break;
            case R.id.btn_updata:
                if (etId.getText().toString().isEmpty()) {
                    Toast.makeText(this, "主键不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //更改主键对应的角色属性
                dao.update(new Person((long) Integer.parseInt(etId.getText().toString()), etNameUpdata.getText().toString(),
                        Integer.parseInt(etAgeUpdata.getText().toString()), false));

                //插入之后更新界面展示
                upDataView(dao.loadAll());

                break;
            case R.id.btn_delete:
                if (TextUtils.isEmpty(etDeleteItem.getText())) {
                    return;
                }
                personList = dao.loadAll();
                if (personList != null && !personList.isEmpty()) {
                    if (Integer.parseInt(etDeleteItem.getText().toString()) <= personList.size()) {
                        dao.delete(personList.get(Integer.parseInt(etDeleteItem.getText().toString()) - 1));
                        //删除之后界面展示
                        upDataView(dao.loadAll());
                    } else {
                        Toast.makeText(this, "超过最大数据长度", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "无数据可以删除", Toast.LENGTH_SHORT).show();
                    tvData.setText("");
                }

                break;
            case R.id.btn_delete_id:
                if (etDeleteItemId.getText().toString().trim().isEmpty()) {
                    return;
                }
                personList = dao.loadAll();
                if (personList != null && !personList.isEmpty()) {
                    for (Person person1 : personList) {
                        if (person1.getId() == (long) Integer.parseInt(etDeleteItemId.getText().toString())) {
                            Log.d("MainActivity", "说明存在这个主键");
                            dao.delete(person1);
                        }
                    }
                }
                upDataView(dao.loadAll());
                break;
            case R.id.btn_query:
                //查询全部的数据
                upDataView(dao.loadAll());

                break;
            case R.id.btn_query_asc:
                //根据年龄升序（中文 标点 按照ascll码排序）
                personsQuery = dao.queryBuilder().orderAsc(PersonDao.Properties.Age).build();
                upDataView(personsQuery.list());

                break;
            case R.id.btn_query_nasc:
                //根据年龄降
                personsQuery = dao.queryBuilder().orderDesc(PersonDao.Properties.Age).build();
                upDataView(personsQuery.list());

                break;
            case R.id.btn_selsect:
                //查询年龄为15岁的所有孩子
                personsQuery = dao.queryBuilder().where(PersonDao.Properties.Age.eq(15)).build();
                upDataView(personsQuery.list());
//                查询所有为15岁的孩子，并且按照ID降序排列出来。
//                personsQuery = dao.queryBuilder().where(PersonDao.Properties.Age.eq(15)).orderDesc(PersonDao.Properties.Id).build();
//                upDataView(personsQuery.list());

                break;
            case R.id.btn_clearAll://删除所有数据
                dao.deleteAll();
                upDataView(dao.loadAll());
                break;
            case R.id.btn_rondom:
                int random = (int) (Math.random() * 100);
                etAge.setText(random + "");
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            GreenDaoUtils.ENCRYPTED = true;
        } else {
            GreenDaoUtils.ENCRYPTED = false;
        }
        dao = GreenDaoUtils.getInstance().getmDaoSession().getPersonDao();
    }
}
