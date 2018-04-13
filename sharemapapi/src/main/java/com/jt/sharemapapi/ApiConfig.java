package com.jt.sharemapapi;

import android.content.Context;

/**
 * <pre>
 *     @author : chenjianquan
 *     e-mail : chenjianquan@chenjianquan.com
 *     time   : 2017/12/27
 *     desc   : api配置属性
 *     version: 1.0
 * </pre>
 */

public class ApiConfig {
    private static final String TAG = "ApiConfig";

    /**
     * 线上类型 1：formal（正式线）；2：qa（测试线）； 3：dev（开发线）；
     */
    private final int mOfficial;
    /**
     * 正式线；
     */
    private final String mDomain;
    /**
     * 测试线；
     */
    private final String mDomainQA;
    /**
     * 开发线
     */
    private final String mDomainDev;

    //We need to enumerate all parameters since ApiConfig supports different subsets of them.

    /**
     * @param official
     * @param domain
     * @param domainQA
     * @param domainDev
     */
    protected ApiConfig(int official,
                        String domain,
                        String domainQA,
                        String domainDev){
        this.mOfficial = official;
        this.mDomain = domain;
        this.mDomainQA = domainQA;
        this.mDomainDev = domainDev;
    }

    /**
     * 获取API域名
     * @return
     */
    public String getDomain()
    {
        if (mOfficial == ApiConstants.OFFICIAL_QA)
        {
            return mDomainQA;
        }else if(mOfficial == ApiConstants.OFFICIAL_DEV) {
            return mDomainDev;
        }

        return mDomain;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("official : ").append(Integer.toString(mOfficial));
        stringBuilder.append("\n");
        stringBuilder.append("domain: ").append(mDomain);
        stringBuilder.append("\n");
        stringBuilder.append("domainQA: ").append(mDomainQA);
        stringBuilder.append("\n");
        stringBuilder.append("domainDev: ").append(mDomainDev);

        return stringBuilder.toString();
    }

    /**
     * class builder
     */
    public static class Builder{
        private int mOfficial;
        private String mDomain;
        private String mDomainQA;
        private String mDomainDev;

        public Builder(){
            this(BaseApi.mApplicationContext);
        }

        Builder(Context context)
        {
            initializeBuilder(context);
        }

        private void initializeBuilder(Context context) {
            this.mOfficial = ApiConstants.OFFICIAL_FORMAL;
            this.mDomain = ApiConstants.API_HOST;
            this.mDomainQA = ApiConstants.API_HOST_QA;
            this.mDomainDev = ApiConstants.API_HOST_DEV;
        }

        /**
         * 环境标识
         * @param official 1：formal（正式线）；2：qa（测试线）；3：dev（开发线）；
         * @return
         */
        public Builder official(int official)
        {
            if (official < 1 ) {
                throw new IllegalArgumentException("official must be 1 or higher");
            }

            this.mOfficial = official;
            return this;
        }

        /**
         * @param domain
         * @return
         */
        public Builder domain(String domain)
        {
            if (domain == null || domain.isEmpty()) {
                throw new IllegalArgumentException("A non-empty domain must be provided");
            }

            this.mDomain = domain;
            return this;
        }

        /**
         * 测试线；
         * @param domainQA
         * @return
         */
        public Builder domainQA(String domainQA)
        {
            if (domainQA == null || domainQA.isEmpty()) {
                throw new IllegalArgumentException("A non-empty domainQA must be provided");
            }

            this.mDomainQA = domainQA;
            return this;
        }

        /**
         * 开发线
         * @param domainDev
         * @return
         */
        public Builder domainDev(String domainDev)
        {
            if (domainDev == null || domainDev.isEmpty()) {
                throw new IllegalArgumentException("A non-empty domainDev must be provided");
            }

            this.mDomainDev = domainDev;
            return this;
        }

        public ApiConfig build(){
            return new ApiConfig(mOfficial,
                mDomain,
                mDomainQA,
                mDomainDev);
        }
    }
}
