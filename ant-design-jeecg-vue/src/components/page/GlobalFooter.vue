<template>
  <div class="footer">
    <div class="links">
      <span target="_blank">弗瑞施净水器管理平台-首页</span>
    </div>
    <!--<div class="copyright">Copyright<a-icon type="copyright"/>2019<span>JEECG开源社区 出品</span> </div>-->
  </div>
</template>

<script>
  export default {
    name: "LayoutFooter"
  }
</script>

<style lang="scss" scoped>
  .footer {
    padding: 0 16px;
    margin: 48px 0 24px;
    text-align: center;

    .links {
      margin-bottom: 8px;

      a {
        color: rgba(0, 0, 0, .45);

        &:hover {
          color: rgba(0, 0, 0, .65);
        }

        &:not(:last-child) {
          margin-right: 40px;
        }
      }
    }
    .copyright {
      color: rgba(0, 0, 0, .45);
      font-size: 14px;
    }
  }
</style>