package jp.co.nri.route.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import jp.co.nri.route.R;
import jp.co.nri.route.base.BaseActivity;
import jp.co.nri.route.base.BaseApplication;
import jp.co.nri.route.di.component.DaggerSignUpComponent;
import jp.co.nri.route.di.module.SignUpModule;
import jp.co.nri.route.presenter.SignUpPresenter;
import jp.co.nri.route.util.AppUtil;
import jp.co.nri.route.view.ISignUpView;

public class SignUpActivity extends BaseActivity<SignUpPresenter> implements ISignUpView {

    @BindView(R.id.tvToolbarBack) TextView tvToolbarBack;
    @BindView(R.id.tvToolbarTitle) TextView tvToolbarTitle;
    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etUserID) EditText etUserID;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.btnSignUp) Button btnSignUp;
    @BindView(R.id.etRepeatPassword) EditText etRepeatPassword;

    @Override
    protected int contentView() {
        return R.layout.activity_sign_up;
    }

    /**
     * View初期化
     */
    @Override
    protected void initView() {
        tvToolbarBack.setVisibility(View.VISIBLE);
        tvToolbarTitle.setVisibility(View.VISIBLE);
        btnSignUp.setEnabled(false);
        MyTextWatcher myTextWatcher = new MyTextWatcher();
        etName.addTextChangedListener(myTextWatcher);
        etUserID.addTextChangedListener(myTextWatcher);
        etPassword.addTextChangedListener(myTextWatcher);
        etRepeatPassword.addTextChangedListener(myTextWatcher);

        setStatusBarColor(R.color.colorAccent, R.color.colorAccent, R.color.colorAccent);
        DaggerSignUpComponent.builder().appComponent(BaseApplication.getApplication().getAppComponent()).signUpModule(new SignUpModule(this)).build().inject(this);
    }

    @OnClick({R.id.tvToolbarBack, R.id.btnSignUp}) void onClick(View v){
        switch (v.getId()){
            case R.id.tvToolbarBack:
                finish();
                break;
            case R.id.btnSignUp:
                presenter.signUp(etName.getText().toString(), etUserID.getText().toString(), etPassword.getText().toString());
                break;
        }
    }


    @Override
    public void closeView() {
        finish();
    }


    private class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(etName.getText().toString()) ||
                    TextUtils.isEmpty(etUserID.getText().toString()) ||
                    TextUtils.isEmpty(etPassword.getText().toString()) ||
                    TextUtils.isEmpty(etRepeatPassword.getText().toString())){
                btnSignUp.setEnabled(false);
            }else{
                btnSignUp.setEnabled((etPassword.getText().toString().equals(etRepeatPassword.getText().toString())));
            }

        }
    }
}
